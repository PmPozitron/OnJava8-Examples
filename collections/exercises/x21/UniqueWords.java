package exercises.x21;

import com.google.common.collect.Comparators;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class UniqueWords {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("collections/SetOperations.java"));
        Map<String, Integer> words = new HashMap();

        for (String line : lines) {
            for (String word : line.split("\\W+")) {
                if (word.trim().length() > 0) {
                    words.merge(word, 1, (current, oneMore) -> current + oneMore);
                }
            }
        }
        List<String> asList = new ArrayList<>(words.keySet());
        Collections.sort(asList, String.CASE_INSENSITIVE_ORDER);
        asList.stream().forEach(item -> System.out.println(item + " " + words.get(item)));
    }
}
