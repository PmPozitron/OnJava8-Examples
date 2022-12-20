package exercises.x8;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class SList<T> {
    Link<T> head;

    public SList(T head) {
        this.head = new Link<>(head);
    }

    @Override
    public String toString() {
        SListIterator<T> iterator = iterator();
        StringBuilder builder = new StringBuilder();
        while(iterator.hasNext()) {
            builder.append(iterator.next().value).append(",");
        }
        return builder.toString();
    }

    public SListIterator<T> iterator() {
        return new SListIterator<>(head);
    }

    private static class SListIterator<T> {
        Link<T> head;
        Link<T> cursor;
        Link<T> previous;

        public SListIterator(Link<T> head) {
            this.head = head;
            this.cursor = head;
        }

        boolean hasNext() {
            return cursor != null;
        }

        void add(T aNew) {
            Link<T> value = new Link<>(aNew);
            if (hasNext()) {
                value.next = cursor.next;
                cursor.next = value;
                previous = cursor;
                cursor = cursor.next;

            } else {
                cursor = value;
                previous.next = cursor;
            }
        }

        Link<T> next() {
            if (!hasNext()) throw new NoSuchElementException();

            Link<T> result = cursor;
            previous = cursor;
            cursor = cursor.next;
            return result;
        }

        void remove() {
            if (!hasNext()) throw new NoSuchElementException();

            previous.next = cursor.next;
            cursor = cursor.next;
        }

        void removeAll(T value) {
            previous = null;
            cursor = head;
            while (hasNext()) {
                Link<T> current = cursor;
                if (current.value.equals(value)) {
                    previous.next = current.next;
                }
                next();

            }
        }
    }

    private static class Link<T> {
        T value;
        Link<T> next;

        public Link(T value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        String[] input = "two three four five six seven eight nine ten".split("\\W+");
        SList<String> theList = new SList<>("one");
        SListIterator<String> iterator = theList.iterator();

        Arrays.stream(input)
                .forEach(iterator::add);

        System.out.println(theList);

        iterator.add(input[2]);
        iterator.add(input[4]);

        System.out.println(theList);

        iterator.remove();
        System.out.println(theList);
        iterator.add(input[3]);
        System.out.println(theList);
        iterator.removeAll(input[3]);
        System.out.println(theList);
    }
}
