package exercises.x24;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class LinkedHashMapFiller {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        Random rnd = new Random();

        rnd.ints(100, 1, 100)
                .peek(System.out::println)
                .forEach(i -> map.put(String.valueOf(i), i));

        System.out.println("*****************");
        map.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue(),
                        (one, two) -> one,
                        TreeMap::new
                )).entrySet().stream()
                .forEach(System.out::println);
    }
}
