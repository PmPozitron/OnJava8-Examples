package exercises.x8;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Gerbil {
    private static int COUNTER = 0;
    private final int gerbilNumber;

    public Gerbil() {
        this.gerbilNumber = COUNTER++;
    }

    public void hop() {
        System.out.println(gerbilNumber + " hopping !");
    }

    public static void main(String[] args) {
        Iterator<Gerbil> iterator = IntStream.range(0, 10)
                .mapToObj(i -> new Gerbil())
                .collect(Collectors.toList())
                .stream()
                .iterator();

        while (iterator.hasNext()) {
            iterator.next().hop();
        }

    }
}
