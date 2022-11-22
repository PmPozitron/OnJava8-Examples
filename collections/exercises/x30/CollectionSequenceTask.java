package exercises.x30;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectionSequenceTask implements Collection<String> {
    private int currentSize;
    private String[] strings;

    @Override
    public Stream<String> stream() {
        return Arrays.stream(Arrays.copyOf(strings, currentSize));
    }

    public CollectionSequenceTask(int size) {
        strings = new String[size];
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof String)) return false;
        String s = (String) o;
        return Arrays.stream(strings)
                .filter(s::equals)
                .findFirst()
                .isPresent();
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(strings).iterator();
    }

    @Override
    public Object[] toArray() {
        String[] result = new String[currentSize];
        System.arraycopy(strings, 0, result, 0, currentSize);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.stream(strings).filter(Objects::nonNull).toArray();
    }

    @Override
    public boolean add(String s) {
        if (currentSize == strings.length) {
            String[] aNew = new String[currentSize * 2];
            System.arraycopy(strings, 0, aNew, 0, currentSize);
            strings = aNew;
        }
        strings[currentSize++] = s;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String)) return false;
        String input = (String) o;

        String[] aNew = Arrays.stream(strings)
                .filter(Objects::nonNull)
                .filter(s -> !s.equals(input))
                .toArray(String[]::new);

        if (strings.length == aNew.length) {
            return false;
        }
        strings = aNew;
        currentSize = aNew.length;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        AtomicInteger i = new AtomicInteger();

        c.stream().forEach(input -> Arrays.stream(strings)
                        .filter(Objects::nonNull)
                        .filter(input::equals)
                        .findAny()
                        .ifPresent(s -> i.incrementAndGet()));

        return i.get() == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        if (c.size() <= strings.length - currentSize) {
            System.arraycopy(c.toArray(), 0, strings, currentSize, c.size());
            currentSize += c.size();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        AtomicInteger result = new AtomicInteger();
        c.stream()
                .forEach(o -> {
                    if (this.remove(o))
                        result.incrementAndGet();
                });

        return result.get() > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        String[] aNew = Arrays.stream(strings)
//                .filter(existing -> c.stream().filter(existing::equals).findAny().isPresent())
                .filter(Objects::nonNull)
                .filter(existing -> c.stream().filter(existing::equals).findAny().isPresent())
                .toArray(String[]::new);
        if (aNew.length < currentSize) {
            strings = aNew;
            currentSize = aNew.length;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        Arrays.fill(strings, null);
        currentSize = 0;
    }


    public static void main(String[] args) {
        CollectionSequenceTask c = new CollectionSequenceTask(100);
        Random rnd = new Random();
        rnd.ints(25, 0, 99).forEach(i -> c.add(String.valueOf(i)));

        c.stream().forEach(i -> System.out.print(i + ","));
        System.out.println("");
        System.out.println(c.size());
        c.add("abc");
        System.out.println(c.size());
        System.out.println(c.contains("abc"));
        System.out.println(c.addAll(Arrays.asList("def", "gfh")));
        c.stream().forEach(i -> System.out.print(i + ","));
        System.out.println("");
        System.out.println(c.remove("def"));
        c.stream().forEach(i -> System.out.print(i + ","));
        System.out.println("");
        System.out.println(c.removeAll(Arrays.asList("gfh", "def")));
        c.stream().forEach(i -> System.out.print(i + ","));
        System.out.println("");
        c.add("xyz");
        System.out.println(c.containsAll(Arrays.asList("abc", "xyz")));
        System.out.println(c.containsAll(Arrays.asList("abc", "def")));
        c.retainAll(Arrays.asList("abc", "xyz"));
        c.stream().forEach(i -> System.out.print(i + ","));


    }
}
