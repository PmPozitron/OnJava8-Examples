package exercises.x28;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComparableTuple<A, B> implements Comparable<ComparableTuple<A,B>>{
    public final A a;
    public final B b;
    public ComparableTuple(A a, B b) { this.a = a; this.b = b; }
    public String rep() { return  a + ", " + b; }
    @Override public String toString() {
        return "(" + rep() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ComparableTuple))
            return false;

        ComparableTuple<?, ?> that = (ComparableTuple<?, ?>) o;
        return Objects.equals(a, that.a) && Objects.equals(b, that.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public int compareTo(ComparableTuple<A,B> o) {
        int result = ((Comparable<A>) a).compareTo(o.a);
        if (result != 0)
            return result;
        return ((Comparable<B>)b).compareTo(o.b);
    }

    public static void main(String[] args) {
        Random rnd = new Random();
        List<ComparableTuple<Integer, Integer>> tuples = IntStream.range(0, 12)
            .mapToObj(i -> new ComparableTuple<>(i, rnd.nextInt(100)))
            .collect(Collectors.toList());
        Collections.shuffle(tuples);

        for (int i = 0; i < tuples.size() - 1; i++) {
            ComparableTuple<Integer, Integer> first = tuples.remove(i++);
            ComparableTuple<Integer, Integer> second = tuples.remove(i);

            System.out.println(first);
            System.out.println(second);
            System.out.println(first.compareTo(second));
        }
    }
}
