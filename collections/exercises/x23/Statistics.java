package exercises.x23;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Statistics {

    public static void main(String[] args) {
        go().entrySet().stream()
                .sorted((one, two) -> two.getValue().compareTo(one.getValue()))
                .forEach(System.out::println);
    }

    private static Map<Integer, Integer> go() {
        Random rand = new Random();
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < 10_000; i++) {
            // Produce a number between 0 and 20:
            int r = rand.nextInt(100);
            Integer freq = m.get(r);               // [1]
            m.put(r, freq == null ? 1 : freq + 1);
        }
        return m;
    }
}
