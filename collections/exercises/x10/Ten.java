package exercises.x10;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/*
Exercise 10:   (2) Change Exercise 9 in the Polymorphism chapter to use an ArrayList to
hold the Rodents and an Iterator to move through the sequence of Rodents.

Exercise 9:   (3) Create an inheritance hierarchy of Rodent: Mouse, Gerbil, Hamster,
etc. In the base class, provide methods that are common to all Rodents, and override these
in the derived classes to perform different behaviors depending on the specific type of
Rodent. Create an array of Rodent, fill it with different specific types of Rodents, and call
your base-class methods to see what happens.
 */
public class Ten {
    public static void main(String[] args) {
        List<Rodent> rodents = List.of(new Mouse(), new Mouse(), new Hamster(), new Hamster(), new Gerbil(), new Gerbil());
        Iterator<Rodent> iterator = rodents.iterator();
        Random rnd = new Random(47);
        while (iterator.hasNext()) {
            Rodent current = iterator.next();
            if (rnd.nextInt(2) == 0) {
                current.sleep();
            } else {
                current.eat();
            }
        }
    }
}

abstract class Rodent {

    public Rodent() {
    }

    protected void sleep() {
        System.out.println(this.getClass().getSimpleName() + " is sleeping");
    }

    protected void eat() {
        System.out.println(this.getClass().getSimpleName() + " is eating");
    }
}

class Mouse extends Rodent {
    @Override
    protected void sleep() {
        super.sleep();
        System.out.println("mouse does not sleep too long");
    }

    @Override
    protected void eat() {
        super.eat();
        System.out.println("mouse likes cheese");

    }
}

class Gerbil extends Rodent {
    @Override
    protected void sleep() {
        super.sleep();
        System.out.println("gerbil sleeps at night");
    }

    @Override
    protected void eat() {
        super.eat();
        System.out.println("I dunno what gerbils eat");
    }
}

class Hamster extends Rodent {
    @Override
    protected void sleep() {
        super.sleep();
        System.out.println("hamster sleeps. that is it. not sure what to add");
    }

    @Override
    protected void eat() {
        super.eat();
        System.out.println("hamsters are stinky");
    }
}
