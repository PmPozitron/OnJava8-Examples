package exercises.x38;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class HashMapLoadFactor {
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int DEFAULT_INITIAL_CAPACITY = 1024;
    static HashMap<Integer, String> theMap = new HashMap<>(DEFAULT_INITIAL_CAPACITY);

    public static void main(String[] args) {
        Random random = new Random();
        ArrayList<Integer> theArray = new ArrayList<>();
        IntStream.range(0, DEFAULT_INITIAL_CAPACITY / 2).forEach(i -> {
            int anInt = random.nextInt(Integer.MAX_VALUE/1000);
            theMap.put(anInt, String.valueOf(anInt));
            theArray.add(anInt);
        });
        System.out.println(theMap.size());
        Collections.shuffle(theArray);
        long start = System.nanoTime();

        theArray.stream().forEach(i -> {
            theMap.get(i);
        });
        System.out.printf("with %d capacity and %f load factor it took %d ns to traverse it\n", DEFAULT_INITIAL_CAPACITY, (float)theMap.size()/DEFAULT_INITIAL_CAPACITY, System.nanoTime() - start);

        int newCapacity = DEFAULT_INITIAL_CAPACITY * 2;
        HashMap theNewMap = new HashMap<>(newCapacity);
        theNewMap.putAll(theMap);

        start = System.nanoTime();
        theArray.stream().forEach(i -> {
            theMap.get(i);
        });
        System.out.printf("with %d capacity and %f load factor it took %d ns to traverse it\n", newCapacity, (float)theMap.size()/newCapacity, System.nanoTime() - start);






    }


}
