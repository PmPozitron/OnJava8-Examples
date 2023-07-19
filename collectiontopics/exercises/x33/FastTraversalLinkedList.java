package exercises.x33;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class FastTraversalLinkedList<E> extends AbstractList<E> {

    LinkedList<E> linked = new LinkedList<>();
    ArrayList<E> array = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            testDrive();
        }
    }

    private static void testDrive() {
        Random rnd = new Random();
        FastTraversalLinkedList<Long> fastList = new FastTraversalLinkedList<>();
        long startTime = System.nanoTime();
        for (int i = 0; i < 4096; i++) {
            fastList.add(rnd.nextLong());
        }

        for (int i = 0; i < 1024; i++) {
            int bound = fastList.size();
            System.out.println(bound);
            fastList.add(Math.abs(rnd.nextInt(bound)), rnd.nextLong());
        }

        for (int i = 0; i < 1024; i++) {
            int bound = fastList.size();
            System.out.println(bound);
            fastList.set(Math.abs(rnd.nextInt(bound)), rnd.nextLong());
        }

        for (int i = 0; i< 1024; i++) {
            int bound = fastList.size();
            System.out.println(bound);
            fastList.remove(Math.abs(rnd.nextInt(bound)));
        }
        long timeTaken = System.nanoTime() - startTime;
        // Collections.sort(fastList.linked);
        // Collections.sort(fastList.array);

        // System.out.printf("time taken = %d, collections are equal %b\n", timeTaken/1_000, fastList.linked.equals(fastList.array));
        System.out.printf("time taken = %d\n", timeTaken/1_000);
    }

    @Override
    public E get(int index) {
        return array.get(index);
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean add(E e) {
        CompletableFuture.runAsync(() -> array.add(e));
        return linked.add(e);
    }

    @Override
    public void add(int index, E element) {
        CompletableFuture.runAsync(() -> array.add(index, element));
        linked.add(element);
    }

    @Override
    public Iterator<E> iterator() {
        return array.iterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return array.listIterator(index);
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public E set(int index, E element) {
        CompletableFuture.runAsync(() -> linked.set(index, element));
        return array.set(index, element);
    }

    @Override
    public E remove(int i) {
        CompletableFuture.runAsync(() -> linked.remove(i));
        return array.remove(i);
    }
}
