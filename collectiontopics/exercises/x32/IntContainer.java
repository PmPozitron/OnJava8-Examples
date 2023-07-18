package exercises.x32;

import java.util.ArrayList;
import java.util.Random;

public class IntContainer {
    int[] container;
    int maxSize = 128;
    int currentSize = 0;

    public IntContainer() {
        this.container = new int[maxSize];
    }

    public IntContainer(int maxSize) {
        this.maxSize = maxSize;
        this.container = new int[maxSize];
    }

    public static void main(String[] args) {
        for (int i = 0; i<100; i++) {
            manipulateContainer();
            manipulateList();
        }
    }

    private static void manipulateContainer() {
        Random rnd = new Random();
        long startTime = System.nanoTime();

        IntContainer container = new IntContainer();
        for (int i = 0; i < 4096; i++) {
            container.add(rnd.nextInt());
        }
        container.get();
        container.get();
        container.get();

        container.get(1);
        container.get(2);
        container.get(3);

        container.get(100);
        container.get(200);
        container.get(300);

        container.get(1000);
        container.get(2000);
        container.get(3000);

        for (int i = 0; i < container.currentSize; i++) {
            container.set(i, container.get(i) + 1);
        }

        container.remove(1);
        container.remove(1);
        container.remove(1);

        container.remove(100);
        container.remove(100);
        container.remove(100);

        container.remove(1000);
        container.remove(1000);
        container.remove(1000);

        System.out.printf("container manipulations took %d\n", (System.nanoTime() - startTime) / 1_000);
    }

    private static void manipulateList() {
        Random rnd = new Random();
        long startTime = System.nanoTime();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4096; i++) {
            list.add(rnd.nextInt());
        }
        list.get(1);
        list.get(2);
        list.get(3);

        list.get(1);
        list.get(2);
        list.get(3);

        list.get(100);
        list.get(200);
        list.get(300);

        list.get(1000);
        list.get(2000);
        list.get(3000);

        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) + 1);
        }

        list.remove(1);
        list.remove(1);
        list.remove(1);

        list.remove(100);
        list.remove(100);
        list.remove(100);

        list.remove(1000);
        list.remove(1000);
        list.remove(1000);

        System.out.printf("list manipulations took %d\n", (System.nanoTime() - startTime) / 1_000);
    }
    private void add(int i) {
        if (currentSize == maxSize) {
            enlarge();
        }
        container[currentSize++] = i;
    }

    private int get() {
        return container[currentSize-1];
    }

    private int get(int i) {
        if (i >= currentSize) {
            throw new IllegalArgumentException();
        }
        return container[i];
    }

    private void set(int i, int aNew) {
        if (i >= currentSize) {
            throw new IllegalArgumentException();
        }
        container[i] = aNew;
    }

    private int remove(int index) {
        if (index >= currentSize) {
            throw new IllegalArgumentException();
        }
        int result = container[index];
        for (int i = index; i < currentSize-1; i++) {
            container[i] = container[i+1];
        }
        return result;
    }

    private void enlarge() {
        int[] current = container;
        int[] aNew = new int[maxSize * 2];
        this.maxSize = maxSize*2;
        System.arraycopy(current, 0, aNew, 0, currentSize);
        container = aNew;
    }


}
