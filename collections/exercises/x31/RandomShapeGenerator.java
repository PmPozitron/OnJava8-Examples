package exercises.x31;

import java.util.Iterator;
import java.util.Random;

public class RandomShapeGenerator implements Iterable<Shape> {

    public static void main(String[] args) {
        int i = 0;
        for (Shape shape : new RandomShapeGenerator()) {
            System.out.println(++i);
            shape.draw();
            shape.erase();
        }
    }

    private int maxElements = 100;

    public RandomShapeGenerator() {
    }

    public RandomShapeGenerator(int count) {
        this.maxElements = count;
    }

    private Random rand = new Random(47);
    public Shape next() {
        switch(rand.nextInt(3)) {
            default:
            case 0: return new Circle();
            case 1: return new Square();
            case 2: return new Triangle();
        }
    }

    @Override
    public Iterator<Shape> iterator() {
        return new ShapeIterator();
    }

    private class ShapeIterator implements Iterator<Shape> {
        private int currentCount;

        @Override
        public boolean hasNext() {
            return currentCount < maxElements;
        }

        @Override
        public Shape next() {
            if (!hasNext()) throw new IllegalStateException("reached the end of the sequence for this iterator");
            currentCount++;
            return RandomShapeGenerator.this.next();
        }
    }
} ///:~

class Shape {
    public void draw() {}
    public void erase() {}
}

class Circle extends Shape {
    @Override public void draw() {
        System.out.println("Circle.draw()");
    }
    @Override public void erase() {
        System.out.println("Circle.erase()");
    }
}
class Square extends Shape {
    @Override public void draw() {
        System.out.println("Square.draw()");
    }
    @Override public void erase() {
        System.out.println("Square.erase()");
    }
}
class Triangle extends Shape {
    @Override public void draw() {
        System.out.println("Triangle.draw()");
    }
    @Override public void erase() {
        System.out.println("Triangle.erase()");
    }
}
