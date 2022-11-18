package exercises.x18;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapOrdering {
    public static void main(String[] args) {
        Random rnd = new Random(47);
        Map<Integer, String> map = rnd.ints(10, 1, 999)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), String::valueOf));

        System.out.printf("hashcode ordering %s\n", map);

        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey()))
                .forEach(entry -> linkedHashMap.put(entry.getKey(), entry.getValue()));

        linkedHashMap.entrySet().stream().forEach(System.out::println);

    }
}
