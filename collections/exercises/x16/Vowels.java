package exercises.x16;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Vowels {

    private static final HashSet<Character> characters = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("collections/SetOperations.java"));
        HashSet<String> processed = new HashSet<>();
        int totalCount = 0;
        for (String line : lines)
            for (String word : line.split("\\W+")) {
                int count = 0;
                if (word.trim().length() > 0) {
                    for (char c : word.toLowerCase().toCharArray()) {
                        if (characters.contains(c)) count++;
                    }
                    if (processed.add(word)) {
                        System.out.println(word + " " + count);
                    }
                    totalCount += count;
                }
            }
        System.out.println(totalCount);
    }
}
