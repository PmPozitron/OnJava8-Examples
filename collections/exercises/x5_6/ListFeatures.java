package exercises.x5_6;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListFeatures {
    public static void main(String[] args) {
        Random rnd = new Random(47);
        List<Integer> integers = Generator.integers(rnd);
        System.out.println(integers);
        Integer seventySeven = 77;
        integers.add(seventySeven);
        System.out.println(integers);
        System.out.println(integers.contains(seventySeven));
        integers.remove(seventySeven);
        System.out.println(integers.indexOf(seventySeven));
        List<Integer> sublist = integers.subList(0, 5);
        System.out.println(integers.containsAll(sublist));
        List<Integer> combined = new ArrayList<>(integers);
        Integer[] arr = new Integer[]{};
        sublist.toArray(arr);
        Collections.addAll(combined, new Integer[]{});
        System.out.println(combined);
        System.out.println(combined.containsAll(integers));
        System.out.println(combined.containsAll(sublist));
        Collections.shuffle(integers);
        System.out.println(integers);
        Collections.sort(integers);
        integers.remove(Integer.valueOf(61));
        System.out.println(integers);

    }
}

class Generator {
    static List<Integer> integers(Random rnd) {
        return IntStream.range(0, 10)
                .mapToObj(i -> rnd.nextInt(100))
                .collect(Collectors.toList());
    }

    static List<Double> doubles(Random rnd) {
        return rnd.doubles(10)
                .mapToObj(Double::valueOf)
                .collect(Collectors.toList());

    }

    static List<Long> longs(Random rnd) {
        return rnd.longs(10)
                .mapToObj(Long::valueOf)
                .collect(Collectors.toList());
    }

    static List<String> strings(Random rnd) {
        List<Character> alphabet = Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g'
        );
        List<String> result = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            StringBuilder string = new StringBuilder();
            int bound = rnd.nextInt(10);
            for (int j = 0; j < bound; j++) {
                string.append(alphabet.get(rnd.nextInt(alphabet.size())));
            }
            result.add(string.toString());
        }
        return result;
    }
}
