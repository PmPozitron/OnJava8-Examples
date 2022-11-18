package exercises.x19;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SetOrdering {
    public static void main(String[] args) {
        Random rnd = new Random(47);
        Set<Integer> set = rnd.ints(10, 1, 999)
                .boxed()
                .collect(Collectors.toSet());

        System.out.printf("hashcode ordering %s\n", set);

        set.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new))
                .stream()
                .forEach(System.out::println);


    }
}
