package exercises.x9;

import java.util.Iterator;

/*
Exercise 9:   (4) Modify innerclasses/Sequence.java so that Sequence works with an
Iterator instead of a Selector.
 */
public class Sequence implements Iterable<Object> {
    private Object[] items;
    private int next = 0;
    private int current = 0;
    public Sequence(int size) {
        items = new Object[size];
    }
    public void add(Object x) {
        if(next < items.length)
            items[next++] = x;
    }
    private class SequenceSelector implements Iterator<Object> {

        @Override
        public boolean hasNext() {
            return current != next;
        }

        @Override
        public Object next() {
            return items[current++];
        }
    }
    public Iterator<Object> iterator() {
        return new SequenceSelector();
    }
    public static void main(String[] args) {
        Sequence sequence = new Sequence(10);
        for(int i = 0; i < 10; i++)
            sequence.add(Integer.toString(i));
        Iterator<Object> iterator = sequence.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
