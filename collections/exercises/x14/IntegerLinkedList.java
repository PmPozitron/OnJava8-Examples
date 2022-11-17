package exercises.x14;

import onjava.Rand;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.IntStream;

public class IntegerLinkedList {
    private LinkedList<Integer> list = new LinkedList<>();

    public static void main(String[] args) {
        new IntegerLinkedList().go();

    }

    private void go() {
        ListIterator<Integer> iterator = list.listIterator();
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                iterator.add(i);
            } else {
                iterator.previous();
                iterator.add(i);
            }
        }
        System.out.println(list);
    }
}
