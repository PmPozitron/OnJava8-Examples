package exercises.x22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.TreeSet;

public class VowelsEncapsulated {

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 1000; i++) {
            go();
        }

    }

    private static void go() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("collections/SetOperations.java"));
        long start = System.nanoTime();
        TreeSet<WordCounter> treeSet = new TreeSet<>();

        for (String line : lines) {
            for (String word : line.split("\\W+")) {
                WordCounter current = new WordCounter(word);
                if (word.trim().length() > 0) {
                    if (treeSet.contains(current)) {
                        List<WordCounter> theList = new ArrayList<>(treeSet);
//                        Collections.sort(theList);    not needed cause list preserves 'sorted' property due to being a copy of sorted set
                        theList.get(Collections.binarySearch(theList, current))
                                .incrementCount();
                    } else {
                        treeSet.add(current);
                    }
                }
            }
        }
//        System.out.println(treeSet);
        System.out.println(System.nanoTime() - start);
    }

    private static class WordCounter implements Comparable<WordCounter> {
        private String word;
        private int counter = 1;

        public WordCounter(String word) {
            this.word = word;
        }

        public WordCounter incrementCount() {
            counter++;
            return this;
        }

        @Override
        public int compareTo(WordCounter o) {
            return this.word.compareTo(o.word);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WordCounter)) return false;
            WordCounter that = (WordCounter) o;
            return word.equals(that.word);
        }

        @Override
        public int hashCode() {
            return Objects.hash(word);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", WordCounter.class.getSimpleName() + "[", "]")
                    .add("word='" + word + "'")
                    .add("counter=" + counter)
                    .toString();
        }
    }
}
