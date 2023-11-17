package exercises.x40_x41_x42;

import onjava.Countries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TestDrive {
    public static void main(String[] args) {
        Random rnd = new Random();
        String[][] countries = Countries.DATA;
        ArrayList<TwoStringsFirst> firsts = new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> {
            int anInt = Math.abs(rnd.nextInt(countries.length));
            System.out.println(anInt);
            String[] entry = countries[anInt];
            firsts.add(new TwoStringsFirst(entry[0], entry[1]));
            firsts.add(new TwoStringsFirst(entry[0], entry[1]));
            System.out.println(entry);

        });

        ArrayList<TwoStringsSecond> seconds = new ArrayList<>();
        ArrayList<TwoStringsThird> thirds = new ArrayList<>();

        firsts.stream().forEach(el -> {
            seconds.add(new TwoStringsSecond(el.first, el.second));
            thirds.add(new TwoStringsThird(el.first, el.second));
        });

        System.out.println("firsts");
        testDrive(firsts);
        System.out.println("seconds");
        testDrive(seconds);
        System.out.println("thirds");
        testDrive(thirds);



    }

    private static void testDrive(List<? extends TwoStringsFirst> list) {
        System.out.println("raw");
        System.out.println(list);
        Collections.sort(list);
        System.out.println("sorted");
        System.out.println(list);
    }
}
