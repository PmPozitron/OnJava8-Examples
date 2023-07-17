package exercises.x30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SortPerformanceComparison {

    public static void main(String[] args) {

        long linkedListStartTime;
        long arrayListStartTime;
        LinkedList<Long> linkedList;
        ArrayList<Long> arrayList;
        for (int i = 0; i < 50; i++) {
            List<Long> longs = getArray(Integer.MAX_VALUE / 1_000);
            // List<Long> longs = getArray( 1_000);
            linkedList = new LinkedList<>(longs);
            arrayList = new ArrayList<>(longs);
            linkedListStartTime = System.nanoTime();
            Collections.sort(linkedList);
            long linkedListEndTime = System.nanoTime();
            arrayListStartTime = System.nanoTime();
            Collections.sort(arrayList);
            long arrayListEndTime = System.nanoTime();

            System.out.printf("%d: sorting linked list took %d, sorting array list took %d, diff is %d\n",
                i, (linkedListEndTime - linkedListStartTime) / 1_000_000, (arrayListEndTime - arrayListStartTime) / 1_000_000,
                ((linkedListEndTime - linkedListStartTime) - (arrayListEndTime - arrayListStartTime)) / 1_000_000);
        }
    }

    private static List<Long> getArray(int size) {
        Long[] longs = new Long[size];
        Random random = new Random();
        Arrays.setAll(longs, i -> random.nextLong());

        return Arrays.asList(longs);
    }
}
