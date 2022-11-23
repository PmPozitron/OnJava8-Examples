package exercises.x32;

import reflection.pets.Pet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

class PetSequence {
    protected reflection.pets.Pet[] pets = new reflection.pets.Pet[]{
            new Cat("Tom"),
            new Rodent("Jerry"),
            new Dog("Scooby Doo"),
            new Cat("Matroskin"),
            new Dog("Sharik"),
            new Rodent("Belka")};
}

public class NonCollectionSequence extends PetSequence implements Iterable<reflection.pets.Pet> {
    public Iterator<reflection.pets.Pet> iterator() {
        return new Iterator<reflection.pets.Pet>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < pets.length;
            }
            @Override
            public reflection.pets.Pet next() {
                if (!hasNext()) throw new IllegalStateException("no items !");
                return pets[index++];
            }
            @Override
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }
    public Iterable<reflection.pets.Pet> reversed() {
        return () -> new Iterator<Pet>() {
            private int index = pets.length;
            @Override
            public boolean hasNext() {
                return index > 0;
            }
            @Override
            public Pet next() {
                if (!hasNext()) throw new IllegalStateException("no items !");
                return pets[--index];
            }
            @Override
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }
    public Iterable<reflection.pets.Pet> random() {

        Set<Integer> takenOut = new HashSet<>();

        return () -> new Iterator<Pet>() {
            @Override
            public boolean hasNext() {
                return takenOut.size()<pets.length;
            }
            @Override
            public Pet next() {
                if (!hasNext()) throw new IllegalStateException("no items !");

                int index = (int)(Math.random() * pets.length);
                while(takenOut.contains(index)) {
                    index = (int)(Math.random() * pets.length);
                }

                takenOut.add(index);
                return pets[index];
            }
            @Override
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }
    public static void main(String[] args) {
        NonCollectionSequence ncs = new NonCollectionSequence();
        for (reflection.pets.Pet pet : ncs) {
            System.out.println(pet);
        }
        System.out.println("***");
        for (reflection.pets.Pet pet : ncs.reversed()) {
            System.out.println(pet);
        }
        System.out.println("***");
        for (reflection.pets.Pet pet : ncs.random()) {
            System.out.println(pet);
        }
        System.out.println("***");
        for (reflection.pets.Pet pet : ncs.random()) {
            System.out.println(pet);
        }


    }
}

class Cat extends reflection.pets.Pet {
    public Cat(String name) { super(name); }
    public Cat() { super(); }
}

class Dog extends reflection.pets.Pet {
    public Dog(String name) { super(name); }
    public Dog() { super(); }
}

class Rodent extends reflection.pets.Pet {
    public Rodent(String name) { super(name); }
    public Rodent() { super(); }
}


