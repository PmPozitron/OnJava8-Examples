package exercises.x4;

import com.sun.tools.javac.util.List;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Generator implements Supplier<String> {

    private final List<String> names = List.of("robocop", "terminator", "predator", "rambo");
    private ListIterator<String> iterator = names.listIterator();

    @Override
    public String get() {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        iterator = names.listIterator();
        return iterator.next();
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        List<Collection> theList = List.of(new ArrayList(), new LinkedList(), new HashSet(), new LinkedHashSet(), new TreeSet());

        IntStream.range(0, 16)
                .mapToObj(i -> generator.get())
                .forEach(item -> {
                    theList.stream().forEach(collection -> collection.add(item));
                });

        theList.stream().forEach(System.out::println);


    }
}
