package exercises.x22.brucessolution;

//: holding/E22_WordsInfo2.java
/****************** Exercise 22 *****************
 * Modify the previous exercise so that it uses a
 * class containing a String and a count field to
 * store each different word, and a Set of these
 * objects to maintain the list of words.
 ***********************************************/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class WordCounter {
    public static final Comparator<WordCounter> CASE_INSENSITIVE_ORDER = (o1, o2) -> o1.word.compareToIgnoreCase(o2.word);
    private final String word;
    private int frequency;

    WordCounter(String word) {
        this.word = word;
        frequency = 1;
    }

    void incFrequency() {
        ++frequency;
    }

    String getWord() {
        return word;
    }

    int getFrequency() {
        return frequency;
    }

    public boolean equals(Object o) {
        return o instanceof WordCounter &&
                word.equals(((WordCounter) o).word);
    }

    public int hashCode() {
        return word.hashCode();
    }
}

public class E22_WordsInfo2 {
    static void updateStat(Iterator<WordCounter> it, WordCounter wc) {
        while (it.hasNext()) {
            WordCounter currentWC = it.next();
            if (currentWC.equals(wc))
                currentWC.incFrequency();
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1000; i++) {
            go();
        }
    }

    private static void go() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("collections/SetOperations.java"));
        long start = System.nanoTime();

        Set<WordCounter> stat = new HashSet<>();
        for (String line : lines) {
            for (String word : line.split("\\W+")) {
                WordCounter wc = new WordCounter(word);
                if (stat.contains(wc))
                    updateStat(stat.iterator(), wc);
                else
                    stat.add(wc);
            }
        }
        List<WordCounter> l = new ArrayList<WordCounter>(stat);
        Collections.sort(l, WordCounter.CASE_INSENSITIVE_ORDER);
//        for (WordCounter wc : l)
//            System.out.println(wc.getWord() + " => " + wc.getFrequency());
        System.out.println(System.nanoTime() - start);


    }

} /* Output: (Sample)
0 => 1
1 => 1
22 => 1
a => 4
add => 1
and => 2
args => 1
...
util => 2
void => 3
W => 1
wc => 9
while => 1
word => 13
WordCounter => 19
words => 1
*///:~