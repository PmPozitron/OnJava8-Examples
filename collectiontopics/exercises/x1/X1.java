package exercises.x1;

import onjava.Countries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class X1 {
    public static void main(String[] args) {
        Random rnd = new Random();
        Supplier<List<Map.Entry<String, String>>> linkedListSupplier = LinkedList::new;
        Supplier<List<Map.Entry<String, String>>> arrayListSupplier = ArrayList::new;

        List<Map.Entry<String, String>> theList = null;

        for (Supplier<List<Map.Entry<String, String>>> supplier : new Supplier[]{linkedListSupplier, arrayListSupplier}) {
            theList = Stream.generate(() -> Countries.DATA[rnd.nextInt(Countries.DATA.length)])
                    .limit(10)
                    .map(entry -> Map.entry(entry[0], entry[1]))
                    .collect(Collector.of(supplier, (list, entry) -> list.add(entry), (one, two) -> {
                        one.addAll(two);
                        return one;
                    }));

            System.out.println(theList);
            Collections.shuffle(theList);
            System.out.println("===");
            System.out.println(theList);
            Collections.shuffle(theList);
        }
    }
}
