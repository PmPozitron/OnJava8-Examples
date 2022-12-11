package exercises.x4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class x4 {
    public static void main(String[] args) throws IOException {

        for (Supplier<Collection<String>> supplier : new Supplier[]{HashSet::new, LinkedHashSet::new, TreeSet::new, ArrayList::new, LinkedList::new}) {
            System.out.println(
                    Files.readAllLines(Paths.get("collections/SetOperations.java"))
                    .stream()
                    .map(s -> s.split("\\W+"))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toCollection(supplier))
            );
            System.out.println("===");
        }

    }
}
