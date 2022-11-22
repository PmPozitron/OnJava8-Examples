package exercises.x28_x29;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.IntStream;

public class PriorityQueueDemo {
    private PriorityQueue<Object> doubles = new PriorityQueue<>();
    private Random rnd = new Random();

    public static void main(String[] args) {
        new PriorityQueueDemo().task28();
        new PriorityQueueDemo().task29();

    }

    private void task28() {
        IntStream.range(0, rnd.nextInt(100))
                .forEach(i -> doubles.offer(rnd.nextDouble()));

        doubles.stream().forEach(System.out::println);
    }

    private void task29() {
        IntStream.range(0, rnd.nextInt(100))
                .forEach(i -> doubles.offer(new ObjectDescendant()));
    }
}

class ObjectDescendant {}
