package exercises.x26;

import exercises.x25.WordsNumerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WordsOrderReinstater {

    public static void main(String[] args) throws IOException {
        Map<String, ArrayList<Integer>> numerator = WordsNumerator.enumerate();
        int size = numerator.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .sorted(Comparator.comparingInt(i -> -i))
                .findFirst()
                .get();

        String[] result = new String[size];

        numerator.entrySet().stream()
                .forEach(entry -> entry.getValue().stream()
                        .forEach(i -> result[i-1] = entry.getKey())
                );
        System.out.println(Arrays.toString(result));
    }
}
