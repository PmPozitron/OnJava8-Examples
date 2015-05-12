#! py -3
"""
TODO: Make sure there's a newline at the end of the extracted files
Extract code examples from TIJ4 Refreshed. Extracts from plain text file.
Creates Ant build.xml file for each subdirectory.
"""
from pathlib import Path
import sys, os
import re
import shutil
import pprint
import difflib
from sortedcontainers import SortedSet
from betools import CmdLine

destination = Path('.') / "ExtractedExamples"
sourceText = Path('.') / "TIJDirectorsCut.txt"
github = Path(r'C:\Users\Bruce\Documents\GitHub\TIJ-Directors-Cut')

startBuild = """\
<?xml version="1.0" ?>

<project default="run">
  <property name="chapter" value="%s"/>
  <property name="excludedfiles" value="%s"/>
  <import file="../Ant-Common.xml"/>

  <target name="run" description="Compile and run" depends="build">
"""

endBuild = """\
  </target>

</project>
"""

def extractExamples():
    if not destination.exists():
        destination.mkdir()
    if not sourceText.exists():
        print("Cannot find", sourceText)
        sys.exit()
    with sourceText.open("rb") as book:
        text = book.read().decode("utf-8", "ignore")
        for listing in (re.findall("^//:.*?///:~", text, re.DOTALL | re.MULTILINE) +
                       re.findall("^#:.*?#:~", text, re.DOTALL | re.MULTILINE)):
            title = listing.splitlines()[0]
            if "//: as a special marker" in title:
                continue
            title = title.split()[1]
            print(title)
            target = destination / Path(title)
            if not target.parent.exists():
                target.parent.mkdir(parents=True)
            if "//:!" in listing:
                listing = "".join(listing.splitlines(keepends=True)[1:-1])
            with target.open("w", newline='') as codeListing:
                codeListing.write(listing)
                codeListing.write("\n")

@CmdLine("x", "clean")
def clean():
    "Remove ExtractedExamples directory"
    print("clean")
    if destination.exists():
        shutil.rmtree(str(destination))


@CmdLine("c", "compare")
def compareWithGithub(shortForm=True):
    "Compare files from Github repository to extracted examples"
    leader = len(str(github)) + 1
    githubfiles = [str(file)[leader:] for file in github.glob("**/*")]
    githubfiles = [ghf for ghf in githubfiles if not ghf.startswith(".git")]
    duplicates = { ghf for ghf in githubfiles if githubfiles.count(ghf) > 1 }
    if duplicates:
        print("duplicates = ", duplicates)

    leader2 = len(str(destination)) + 1
    destfiles = [str(file)[leader2:] for file in destination.glob("**/*")]
    duplicates = { ghf for ghf in destfiles if destfiles.count(ghf) > 1 }
    if duplicates:
        print("duplicates = ", duplicates)

    githubfiles = SortedSet(githubfiles)
    destfiles = SortedSet(destfiles)

    runOutput = re.compile("/\* Output:.*///:~", re.DOTALL)
    differ = difflib.Differ()

    def rstrip(lines):
        return [line.rstrip() for line in lines]

    def show(lines, sep="#"):
        sys.stdout.writelines(lines)
        print("\n" + sep * 80)


    inBoth = [f for f in destfiles.intersection(githubfiles) if f.endswith(".java")]
    for f in inBoth:
        with (github / f).open() as ghf:
            with (destination / f).open() as dstf:
                ghblock = runOutput.sub("", ghf.read())
                dstblock = runOutput.sub("", dstf.read())
                if ghblock.strip() == dstblock.strip():
                    continue
                ghtext = ghblock.splitlines(keepends=True)
                dsttext = dstblock.splitlines(keepends=True)
                print("[[[", f, "]]]")
                if shortForm:
                    show([ln + "\n" for ln in difflib.context_diff(rstrip(ghtext), rstrip(dsttext))], sep="=")
                else:
                    show([ln + "\n" for ln in differ.compare(rstrip(ghtext), rstrip(dsttext))], sep="=")



def githubDirs():
    leader = len(str(github)) + 1
    buildfiles = [str(file)[leader:] for file in github.glob("**/build.xml")]
    return {str((github / f).parent)[leader:] for f in buildfiles}


def destDirs(pattern="**"):
    leader = len(str(destination)) + 1
    return {str(file)[leader:] for file in destination.glob(pattern)}


@CmdLine("a", "ant")
def copySupplementalFilesFromGithub():
    "Copy ant build files from Github repository to extracted examples"
    shutil.copy(str(github / "build.xml"), str(destination))
    shutil.copy(str(github / "Ant-Common.xml"), str(destination))
    for face in (github / "gui").glob("*.gif"):
        shutil.copy(str(face), str(destination / "gui"))
    for verifier in ["OutputGenerator.py", "OutputVerifier.py"]:
        shutil.copy(str(github/verifier), str(destination))
    patterns = destination / "patterns"
    trash = patterns / "recycleap" / "Trash.dat"
    shutil.copy(str(trash), str(patterns / "recycleb"))
    shutil.copy(str(trash), str(patterns / "dynatrash"))



class CodeFileOptions(object):
    """docstring for CodeFileOptions"""
    def __init__(self, codeFile):
        "Should probably use regular expressions for parsing instead"
        self.codeFile = codeFile
        self.msg = ""

        self.cmdargs = None
        if "{Args:" in self.codeFile.code:
            for line in self.codeFile.lines:
                if "{Args:" in line:
                    self.cmdargs = line.split("{Args:")[1].strip()
                    self.cmdargs = self.cmdargs.rsplit("}", 1)[0]

        self.runbyhand = "{RunByHand}" in self.codeFile.code

        self.exclude = None
        if "{CompileTimeError}" in self.codeFile.code:
            self.exclude = self.codeFile.name + ".java"
            if self.codeFile.subdirs:
                self.exclude = '/'.join(self.codeFile.subdirs) + '/' + self.exclude
            print(self.exclude)

        self.continue_on_error = None
        if "{ThrowsException}" in self.codeFile.code:
            self.continue_on_error = True
            self.msg = "* Exception was Expected *"


        self.alternatemainclass = None
        if "{main: " in self.codeFile.code:
             for line in self.codeFile.lines:
                if "{main:" in line:
                    self.alternatemainclass = line.split("{main:")[1].strip()
                    self.alternatemainclass = self.alternatemainclass.rsplit("}", 1)[0]

        self.timeout = None
        if "{TimeOut:" in self.codeFile.code:
             for line in self.codeFile.lines:
                if "{TimeOut:" in line:
                    self.timeout = line.split("{TimeOut:")[1].strip()
                    self.timeout = self.timeout.rsplit("}", 1)[0]
                    self.continue_on_error = True
        elif "//: gui/" in self.codeFile.code or "//: swt/" in self.codeFile.code or "{TimeOutDuringTesting}" in self.codeFile.code:
            self.timeout = "4000"
            self.continue_on_error = True
            self.msg = "* Timeout for Testing *"

    def classFile(self):
        start = """    <jrun cls="%s" """
        if self.alternatemainclass:
            return start % self.alternatemainclass
        if self.codeFile.package:
            return start % (self.codeFile.packageName() + '.' + self.codeFile.name)
        return start % self.codeFile.name

    def dirPath(self):
        if self.codeFile.package:
            return """dirpath="%s" """ % self.codeFile.relpath
        return ""

    def arguments(self):
        if self.cmdargs:
            return """arguments="%s" """ % self.cmdargs
        return ""

    def failOnError(self):
        if self.continue_on_error:
            return """failOnError='false' """
        return ""

    def timeOut(self):
        if self.timeout:
            return """timeOut='%s' """ % self.timeout
        return ""

    def message(self):
        if self.msg:
            return """msg='%s' """ % self.msg
        return ""

    def createRunCommand(self):
        return self.classFile() + self.dirPath() + self.arguments() + self.failOnError() + self.timeOut() + self.message() + "/>\n"



class CodeFile:
    def __init__(self, javaFile, chapterDir):
        self.chapter_dir = chapterDir
        self.java_file = javaFile
        self.subdirs = str(javaFile.parent).split("\\")[2:]
        with javaFile.open() as j:
            self.code = j.read()
        self.lines = self.code.splitlines()
        self.main = None
        if "public static void main" in self.code:
            self.main = True
        self.package = None
        if "package " in self.code:
            for line in self.lines:
                if line.startswith("package ") and line.strip().endswith(";"):
                    self.package = line
                    break
        self.tagLine = self.lines[0][4:]
        self.relpath = '../' + '/'.join(self.tagLine.split('/')[:-1])
        self.name = javaFile.name.split('.')[0]
        self.options = CodeFileOptions(self)

    def run_command(self):
        if not self.main:
            return ""
        return self.options.createRunCommand()

    def __repr__(self):
        result = self.tagLine
        if self.package:
            result += "\n" + self.package
        result += "\n"
        return result

    def packageName(self):
        return self.package.split()[1][:-1]

    def checkPackage(self):
        if not self.package:
            return True
        path = '.'.join(self.tagLine.split('/')[:-1])
        packagePath = self.packageName()
        return path == packagePath




class Chapter:
    def __init__(self, dir):
        self.dir = dir
        self.code_files = [CodeFile(javaFile, dir) for javaFile in dir.glob("**/*.java")]
        self.excludes = [cf.options.exclude for cf in self.code_files if cf.options.exclude]

    def __repr__(self):
        result = "-" * 80
        result += "\n" + str(self.dir) + "\n"
        result += "-" * 80
        result += "\n"
        for cf in self.code_files:
            result += str(cf.name) + "\n"
        return result

    def checkPackages(self):
        for cf in self.code_files:
            if not cf.checkPackage():
                print("BAD PACKAGE")
                print("\t", cf.tagLine)
                print("\t", cf.package)
                print("\n".join(cf.lines))

    def makeBuildFile(self):
        buildFile = startBuild % (self.dir.name, " ".join(self.excludes))
        for cf in self.code_files:
            if any([cf.name + ".java" in f for f in self.excludes]) or cf.options.runbyhand:
                continue
            buildFile += cf.run_command()
        buildFile += endBuild
        with (self.dir / "build.xml").open("w") as buildxml:
            buildxml.write(buildFile)



@CmdLine("m", "makeant")
def createAntFiles():
    "Make ant files that don't exist"
    chapters = [Chapter(fd) for fd in destination.glob("*") if fd.is_dir() if not (fd / "build.xml").exists()]
    for chapter in chapters:
        chapter.checkPackages()
        chapter.makeBuildFile()


@CmdLine("f", "find")
def findNonJavaFiles():
    "Find non-java files in TIJDirectorsCut.txt"
    if not sourceText.exists():
        print("Cannot find", sourceText)
        sys.exit()
    with sourceText.open("rb") as book:
        text = book.read().decode("utf-8", "ignore")
        for listing in re.findall("^//:.*?///:~", text, re.DOTALL | re.MULTILINE):
            title = listing.splitlines()[0].strip()
            if not title.endswith(".java"):
                print(title)


@CmdLine('e', "extract" )
def extractAndCreateBuildFiles():
    "Clean, then extract examples from TIJDirectorsCut.txt, build ant files"
    clean()
    extractExamples()
    copySupplementalFilesFromGithub()
    createAntFiles()
    os.chdir("ExtractedExamples")
    with open("run.bat", 'w') as run:
        run.write(r"python ..\Validate.py -p" + "\n")
        run.write(r"powershell .\runall.ps1" + "\n")
    with open("v.bat", 'w') as run:
        run.write(r"python ..\Validate.py %*" + "\n")

if __name__ == '__main__':  CmdLine.run()