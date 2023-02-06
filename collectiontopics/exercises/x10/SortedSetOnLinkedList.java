package exercises.x10;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SortedSetOnLinkedList<T> implements SortedSet<T> {

    Comparator<T> comparator;
    LinkedList<T> storage = new LinkedList<>();

    public SortedSetOnLinkedList() {
    }

    public SortedSetOnLinkedList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        Comparator<T> cmpr = comparator == null
                ? (Comparator<T>) Comparator.naturalOrder()
                : comparator;
        if (cmpr.compare(fromElement, toElement) > 0)
            throw new IllegalArgumentException("fromElement should be smaller than toElement");

        SortedSetOnLinkedList<T> result = new SortedSetOnLinkedList<>(comparator);
        ListIterator<T> listIterator = storage.listIterator(Collections.binarySearch(storage, fromElement, cmpr));
        T item = null;
        while (listIterator.hasNext() && ! (item = listIterator.next()).equals(toElement)) {
            result.add(item);
        }
        return result;
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSetOnLinkedList<T> result = new SortedSetOnLinkedList<>(comparator);
        Comparator<T> cmpr = comparator == null
                ? (Comparator<T>) Comparator.naturalOrder()
                : comparator;
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (cmpr.compare(t, toElement) <= 0) {
                result.add(t);
            }
        }

        return result;
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSetOnLinkedList<T> result = new SortedSetOnLinkedList<>(comparator);
        int index = Collections.binarySearch(storage, fromElement, comparator);
        if (index < 0) {
            throw new IllegalArgumentException("fromElement not contained in the storage");
        }
        ListIterator<T> listIterator = storage.listIterator(index);
        while (listIterator.hasNext()) {
            result.add(listIterator.next());
        }
        return result;
    }

    @Override
    public T first() {
        return storage.getFirst();
    }

    @Override
    public T last() {
        return storage.getLast();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return Collections.binarySearch(storage, (T)o, comparator) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return storage.iterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (isEmpty()) {
            storage.add(t);
            return true;
        }

        int insertionPoint = Collections.binarySearch(storage, t, comparator);
        if (insertionPoint >= 0) {
            return false;
        } else {
            insertionPoint = -1 * (insertionPoint + 1);
            storage.add(insertionPoint, t);
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty()) return false;

        int indexOfBeingDeleted = Collections.binarySearch(storage, (T)o, comparator);
        if (indexOfBeingDeleted < 0) {
            return false;
        } else {
            storage.remove(indexOfBeingDeleted) ;
            return true;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o  : c) {
            if (! contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (Object o : c) {
            if (! add((T)o)) modified = false;
        }

        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        List<T> sortedInput = new LinkedList<>();
        for (Object o : c) {
            sortedInput.add((T)o);
        }
        Collections.sort(sortedInput, comparator);

        Iterator<T> iterator = storage.iterator();
        while(iterator.hasNext()) {
            T t = iterator.next();
            int index = Collections.binarySearch(sortedInput, t, comparator);
            if (index < 0) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            modified = remove(o);
        }
        return modified;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public String toString() {
        return storage.toString();
    }

    public static void main(String[] args) {
        SortedSetOnLinkedList<String> sortedSet = new SortedSetOnLinkedList<>();
        sortedSet.add("abc");
        sortedSet.add("bcd");
        sortedSet.add("cde");

        System.out.println(sortedSet);
        sortedSet.add("abc");
        sortedSet.add("aaa");
        System.out.println(sortedSet);

        sortedSet.remove("aaa");
        sortedSet.remove("bbb");
        System.out.println(sortedSet);

        sortedSet.addAll(Arrays.asList("def", "fgh"));
        System.out.println(sortedSet);

        sortedSet.removeAll(Arrays.asList("abc", "def"));
        System.out.println(sortedSet);

        sortedSet.addAll(Arrays.asList("aaa", "bbb", "ccc"));
        sortedSet.retainAll(Arrays.asList("bcd", "fgh", "bbb"));
        System.out.println(sortedSet);

        sortedSet.addAll(Arrays.asList("aaa", "aab", "aba", "bbb", "bbc", "bcb"));
        System.out.println(sortedSet);

        System.out.println(sortedSet.headSet("bcb"));
        System.out.println(sortedSet.tailSet("bbb"));
        System.out.println(sortedSet.subSet("aab", "bcb"));

    }
}
