package exercises.x20;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Vowels {

    private static final HashSet<Character> characters = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("collections/SetOperations.java"));
//        List<String> lines = getSeveralAlphabetStrings(5);
        HashMap<Character, Integer> countMap = new HashMap<>();
        for (String line : lines)
            for (String word : line.split("\\W+")) {
                if (word.trim().length() > 0) {
                    for (char c : word.toLowerCase().toCharArray()) {
                        if (characters.contains(c)) {
                            countMap.merge(c, 1, (fist, second) -> fist + second);
                        }
                    }
                }
            }
        System.out.println(countMap);
    }

    private static List<String> getSeveralAlphabetStrings(int num) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] result = new String[num];
        Arrays.fill(result, alphabet);
        return Arrays.asList(result);
    }
}
