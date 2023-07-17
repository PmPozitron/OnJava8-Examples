package exercises.x31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringContainer {

    String[] container;
    int maxSize = 128;
    int currentSize = 0;

    public StringContainer() {
        this.container = new String[maxSize];
    }

    public StringContainer(int maxSize) {
        this.maxSize = maxSize;
        this.container = new String[maxSize];
    }

    public static void main(String[] args) {
        int size = 4096;

        for (int i = 0; i < 100; i++) {
            manipulateList(size);
            manipulateContainer(size);
        }
    }

    private static void manipulateList(int size) {
        ArrayList<String> ethalon = new ArrayList<>();
        List<String> donor = strings(size);
        long arrayListStartTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            ethalon.add(donor.get(i));
        }
        ethalon.get(1);
        ethalon.get(100);
        ethalon.get(1000);

        ethalon.set(1, "new");
        ethalon.set(100, "new");
        ethalon.set(1000, "new");

        ethalon.remove(1000);
        ethalon.remove(100);
        ethalon.remove(1);
        ethalon.remove(1);
        ethalon.remove(1);
        ethalon.remove(1);
        ethalon.remove(1);
        ethalon.remove(1);
        ethalon.remove(1);
        System.out.printf("array list manipulations took %d\n", (System.nanoTime() - arrayListStartTime) / 1_000);
    }

    private static void manipulateContainer(int size) {
        StringContainer container = new StringContainer();
        List<String> donor = strings(size);
        long containerStartTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            container.add(donor.get(i));
        }
        container.get(1);
        container.get(100);
        container.get(1000);

        container.set(1, "new");
        container.set(100, "new");
        container.set(1000, "new");

        container.remove(1000);
        container.remove(100);
        container.remove(1);
        container.remove(1);
        container.remove(1);
        container.remove(1);
        container.remove(1);
        container.remove(1);
        container.remove(1);
        System.out.printf("container manipulations took %d\n", (System.nanoTime() - containerStartTime) / 1_000);
    }

    private static List<String> strings(int size) {
        Random rnd = new Random();
        String[] result = new String[size];
        Arrays.setAll(result, i -> String.valueOf(rnd.nextLong()));
        return Arrays.asList(result);
    }

    public void add(String aString) {
        if (currentSize == maxSize) {
            enlarge();
        }
        container[currentSize++] = aString;
    }

    public String get() {
        if (currentSize == 0)
            throw new IllegalStateException();
        return container[currentSize-1];
    }

    public String get(int i) {
        if (i >= currentSize)
            throw new IllegalArgumentException();
        return container[i];
    }

    public void set(int i, String s) {
        if (currentSize == 0)
            throw new IllegalStateException();
        if (i >= currentSize)
            throw new IllegalArgumentException();
        container[i] = s;
    }

    public String remove(int i) {
        if (currentSize == 0)
            throw new IllegalStateException();
        if (i >= currentSize)
            throw new IllegalArgumentException();
        String result = container[i];
        for (int j = i; j < --currentSize; j++) {
            container[j] = container[j+1];
        }

        return result;
    }

    private void enlarge() {
        // System.out.println("enlarging");
        String[] anOld = container;
        String[] aNew = new String[maxSize * 2];
        System.arraycopy(anOld, 0, anOld, 0, currentSize);
        container = aNew;
        maxSize = container.length;
    }





}
