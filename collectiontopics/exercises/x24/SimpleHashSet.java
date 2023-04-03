package exercises.x24;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleHashSet<T> extends AbstractSet<T> {
    int numberOfBuckets = 512;
    ArrayList<T>[] buckets;

    public SimpleHashSet() {
        buckets = new ArrayList[numberOfBuckets];
    }

    public SimpleHashSet(int numberOfBuckets) {
        this.numberOfBuckets = numberOfBuckets;
        new SimpleHashSet<>();
    }

    class SetIterator implements Iterator<T> {
        T next;
        int bucketIndex;
        int elementIndex;
        boolean hasNext;

        public SetIterator() {
            for (int i = 0; i < numberOfBuckets && ! hasNext; i++) {
                if (buckets[i] != null && ! buckets[i].isEmpty()) {
                    bucketIndex = i;
                    for (int j = 0; j < buckets[i].size() && ! hasNext; j++) {
                        if (buckets[i].get(j) != null) {
                            next = buckets[i].get(j);
                            elementIndex = j;
                            hasNext = true;
                        }
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public T next() {
            if (! hasNext) {
                throw new NoSuchElementException();
            }
            T result = next;
            next = null;
            int j = elementIndex + 1;

            for (int i = bucketIndex; i < numberOfBuckets; i++) {
                if (buckets[i] == null || buckets[i].size() <= j) {
                    j = 0;
                    continue;
                }
                if (buckets[i].size() >= j) {
                    bucketIndex = i;
                    elementIndex = j;
                    next = buckets[i].get(j);
                    hasNext = true;
                    break;
                }
            }
            if (next == null) {
                hasNext = false;
                bucketIndex = -1;
                elementIndex = -1;
            }

            return result;
        }

        @Override
        public void remove() {
            if (hasNext) {
                buckets[bucketIndex].remove(elementIndex);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new SetIterator();
    }

    @Override
    public int size() {
        return Arrays.stream(buckets)
            .filter(Objects::nonNull)
            .mapToInt(List::size)
            .sum();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(T t) {
        Objects.requireNonNull(t);
        int index = Math.abs(t.hashCode() % numberOfBuckets);
        if (buckets[index]==null)
            buckets[index] = new ArrayList<>();

        if (buckets[index].contains(t))
            return false;

        return buckets[index].add(t);
    }

    @Override
    public boolean remove(Object o) {
        Objects.requireNonNull(o);
        int index = Math.abs(o.hashCode() % numberOfBuckets);
        if (buckets[index]==null)
            return false;

        return buckets[index].remove(o);
    }

    @Override
    public boolean contains(Object o) {
        Objects.requireNonNull(o);
        int index = Math.abs(o.hashCode() % numberOfBuckets);
        if (buckets[index]==null)
            return false;

        return buckets[index].contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream()
            .filter(item -> ! contains(item))
            .findAny()
            .isPresent();
    }

    @Override
    public void clear() {
        buckets = new ArrayList[numberOfBuckets];
    }

    public static void main(String[] args) {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        IntStream.range(0, 10)
            .forEach(i -> set.add(UUID.randomUUID().toString()));
        System.out.println(set);
        Iterator<String> iterator = set.iterator();

        String uuid = iterator.next();
        System.out.println(set.contains(uuid));
        System.out.println(set.add(uuid));
        System.out.println(set.size());
        System.out.println(set.remove(uuid));
        System.out.println(set.size());

        System.out.println(iterator.next());
        iterator.remove();
        System.out.println(set.size());



    }
}
