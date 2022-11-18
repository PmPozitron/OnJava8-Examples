package exercises.x17;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Gerbil {
    private static int COUNTER = 0;
    private final int gerbilNumber;

    public Gerbil() {
        this.gerbilNumber = COUNTER++;
    }

    public int getGerbilNumber() {
        return gerbilNumber;
    }

    public void hop() {
        System.out.println(gerbilNumber + " hopping !");
    }

    public static void main(String[] args) {
        Map<Integer, Gerbil> map = IntStream.range(0, 10)
                .mapToObj(i -> new Gerbil())
                .collect(Collectors.toMap(Gerbil::getGerbilNumber, Function.identity()));

        Iterator<Map.Entry<Integer, Gerbil>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Gerbil current = iterator.next().getValue();
            System.out.print(current.getGerbilNumber() + " ");
            current.hop();
        }

        map.entrySet().stream().iterator().forEachRemaining(item -> item.getValue().hop());
    }
}
