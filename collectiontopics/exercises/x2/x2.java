package exercises.x2;

import onjava.Countries;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class x2 {
    public static void main(String[] args) {
        Stream<String[]> stream = Arrays.stream(Countries.DATA)
                .filter(item -> item[0].startsWith("A"));
//                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1],
//                        (v1, v2) -> {throw new IllegalStateException("that can not be");}));

        Map<String, String> theMap = stream.collect(Collectors.toMap(pair -> pair[0], pair -> pair[1], (v1, v2) -> {throw new IllegalStateException("that can not be");}));
        System.out.println(theMap + " " + theMap.size());

        Set<String> theSet = theMap.keySet();
        System.out.println(theSet + " " + theSet.size());
    }
}
