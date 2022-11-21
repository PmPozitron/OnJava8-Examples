package exercises.x25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WordsNumerator {

    public static void main(String[] args) throws IOException {
        System.out.println(enumerate());
    }

    public static Map<String, ArrayList<Integer>> enumerate() throws IOException {

        Map<String, ArrayList<Integer>> numerator = new HashMap<>();
        AtomicInteger counter = new AtomicInteger(1);
        List<String> lines = Files.readAllLines(Paths.get("collections/SetOperations.java"));
        lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .filter(word -> !"".equals(word))
                .forEach(word -> numerator.merge(word,
                        new ArrayList<>(Arrays.asList(counter.getAndIncrement())),
                        (one, two) -> {
                            Collections.addAll(one, two.toArray(new Integer[0]));
                            return one;
                        }));

        return numerator;


    }
}
