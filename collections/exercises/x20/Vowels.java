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
        HashMap<Character, Integer> wordMap = new HashMap<>();
        HashMap<String, HashMap<Character, Integer>> processedWords = new HashMap<>();

        for (String line : lines) {
            for (String word : line.split("\\W+")) {
                wordMap.clear();

                if (word.trim().length() > 0) {
                    if (processedWords.containsKey(word)) {
                        HashMap<Character, Integer> currentValue = processedWords.get(word);
                        currentValue.entrySet().stream()
                                .forEach(item -> {
                                    countMap.merge(item.getKey(), item.getValue(), (current, more) -> current + more);
                                });
                        continue;
                    }
                    for (char c : word.toLowerCase().toCharArray()) {
                        if (characters.contains(c)) {
                            countMap.merge(c, 1, (fist, second) -> fist + second);
                            wordMap.merge(c, 1, (fist, second) -> fist + second);
                        }
                    }
                    processedWords.put(word, new HashMap<>(wordMap));
                }
                if (!wordMap.isEmpty()) {
                    System.out.println(word + " " + wordMap);
                }
            }
        }
        System.out.println(countMap);
        System.out.println(processedWords);
    }

    private static List<String> getSeveralAlphabetStrings(int num) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] result = new String[num];
        Arrays.fill(result, alphabet);
        return Arrays.asList(result);
    }
}
