package exercises.x12;

import onjava.Rand;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;

public class IntegersIterator {
    public static void main(String[] args) {
        List<Integer> first = new Random()
                .ints(50)
                .collect(LinkedList::new, Queue::offer, Collection::addAll);
        Deque<Integer> second = new LinkedList<>();

        ListIterator<Integer> firstIterator = first.listIterator();


        while (firstIterator.hasNext()) {
            second.addFirst(firstIterator.next());
        }
        System.out.println(first);
        System.out.println(second);

        List<Integer> third = new LinkedList<>();
        firstIterator = first.listIterator(first.size());

        while(firstIterator.hasPrevious()) {
            third.add(firstIterator.previous());
        }

        System.out.println(third);
    }
}
