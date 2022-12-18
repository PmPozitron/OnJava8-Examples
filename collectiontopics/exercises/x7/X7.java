package exercises.x7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class X7 {

    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>(onjava.Countries.names());
        LinkedList<String> linkedList = new LinkedList<>(onjava.Countries.names());

        System.out.println("arrayList");
        for (Iterator<String> iterator = arrayList.iterator(); iterator.hasNext();) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println("\nlinkedList");
        for (Iterator<String> iterator = linkedList.iterator(); iterator.hasNext();) {
            System.out.print(iterator.next() + " ");
        }

        Random rnd = new Random();
        for (ListIterator<String> listIterator = arrayList.listIterator(); listIterator.hasNext();) {
            ListIterator<String> linkedListIterator = linkedList.listIterator(rnd.nextInt(linkedList.size()));
            linkedListIterator.add(listIterator.next());
        }

        System.out.println("\nafter insert");
        System.out.println(linkedList);

        linkedList = new LinkedList<>(onjava.Countries.names());
        ListIterator<String> linkedListIterator = linkedList.listIterator();
        for (ListIterator<String> arrayListIterator = arrayList.listIterator(arrayList.size() - 1); arrayListIterator.hasPrevious() && linkedListIterator.hasNext(); ) {
            arrayListIterator.add(linkedListIterator.next());
        }
        System.out.println("\nafter second insert");
        System.out.println(arrayList);

    }
}
