package exercises.x13;

import exercises.x12.AssociativeArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordCounter {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("collections/SetOperations.java"));
        List<String> words = lines.stream()
                .map(string -> string.split("\\W+"))
                .flatMap(array -> Arrays.stream(array))
                .collect(Collectors.toList());

        AssociativeArray<String, Integer> map = new AssociativeArray<>(words.size());

        words.forEach(word -> {
            word.trim();
            if (map.get(word) != null) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        });

        System.out.println(map);
    }
}
