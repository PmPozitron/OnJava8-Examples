package exercises.x11;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Random;

public class IntegerWrapper implements Comparable<IntegerWrapper> {

    public IntegerWrapper(int anInt) {
        this.anInt = anInt;
    }

    int anInt;

    @Override
    public int compareTo(IntegerWrapper o) {
        return Integer.compare(this.anInt, o.anInt);
    }

    public static void main(String[] args) {
        PriorityQueue<IntegerWrapper> queue = new PriorityQueue<>();
        new Random(47)
                .ints(100, -1000, +1000)
                .forEach(i -> queue.add(new IntegerWrapper(i)));

        while (! queue.isEmpty()) {
            System.out.println(queue.poll().anInt);
        }
    }
}
