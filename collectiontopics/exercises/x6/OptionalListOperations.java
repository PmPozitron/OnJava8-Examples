package exercises.x6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class OptionalListOperations {


    public static void main(String[] args) {
        String input = "one two three";
        Supplier<List>[] suppliers = new Supplier[]{LinkedList::new, ArrayList::new};
        OptionalListOperations listOperations = new OptionalListOperations();

        Arrays.stream(suppliers).forEach(listSupplier -> {
            List theList = listSupplier.get();
            theList.addAll(Arrays.asList(input.split(" ")));
            System.out.println(listSupplier);
            System.out.println("starting test");
            long start = System.nanoTime();
            IntStream.range(0, 1000).forEach(i -> listOperations.test(theList));
            System.out.println(theList.getClass().getSimpleName() + ":" + (System.nanoTime() - start));
        });



    }

    private void test(String description, Runnable test) {
        try {
            test.run();

        } catch (Throwable throwable) {
            System.out.println(throwable.getClass().getSimpleName() + " occurred in " + description);
        }
    }

    private void test(List<String> aList) {
        System.out.println(aList);
        test(aList.getClass().getSimpleName().concat(" add by index"), () -> aList.add(0, "added by index 0"));
        System.out.println("===");
        System.out.println(aList);
        test(aList.getClass().getSimpleName().concat(" set by index"), () -> aList.set(0, "updated by index 0"));
        System.out.println("===");
        System.out.println(aList);
        test(aList.getClass().getSimpleName().concat(" remove by index"), () -> aList.remove(0));
        System.out.println("===");
        System.out.println(aList);
    }
}
