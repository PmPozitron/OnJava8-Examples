package exercises.x1;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
Exercise 1:   (2) Create a new class called Gerbil with an int gerbilNumber that’s
initialized in the constructor. Give it a method called hop( ) that displays which gerbil
number this is, and that it’s hopping. Create an ArrayList and add Gerbil objects to the
List. Now use the get( ) method to move through the List and call hop( ) for each Gerbil.
 */
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
        IntStream.range(0, 10)
                .mapToObj(i -> new Gerbil())
                .collect(Collectors.toList())
                .stream()
                .forEach(Gerbil::hop);
    }
}
