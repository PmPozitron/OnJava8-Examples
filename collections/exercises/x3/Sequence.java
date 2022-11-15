package exercises.x3;

// innerclasses/Sequence.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// Holds a sequence of Objects

import java.util.LinkedList;

interface Selector {
    boolean end();
    Object current();
    void next();
}

public class Sequence {
    private LinkedList items = new LinkedList();

    public void add(Object x) {
        items.add(x);
    }
    private class SequenceSelector implements Selector {
        private int i = 0;
        @Override
        public boolean end() { return null == items.peekLast(); }
        @Override
        public Object current() { return items.pollLast(); }
        @Override
        public void next() {  }
    }
    public Selector selector() {
        return new SequenceSelector();
    }
    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        for(int i = 0; i < 10; i++)
            sequence.add(Integer.toString(i));
        Selector selector = sequence.selector();
        while(!selector.end()) {
            System.out.print(selector.current() + " ");
        }
    }
}
/* Output:
0 1 2 3 4 5 6 7 8 9
*/
