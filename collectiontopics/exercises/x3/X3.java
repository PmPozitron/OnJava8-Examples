package exercises.x3;

import onjava.Countries;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class X3 {
    public static void main(String[] args) {
        String[][][] triple = new String[][][]{Countries.DATA, Countries.DATA, Countries.DATA};
        for (Supplier<Set<String>> supplier : new Supplier[]{HashSet::new, LinkedHashSet::new, TreeSet::new}) {
            System.out.println(
                    Arrays.stream(triple)
                            .flatMap(item -> Arrays.stream(item))
                            .map(item -> item[0])
                            .collect(Collectors.toCollection(supplier))
            );

            System.out.println("===");
        }
    }
}
