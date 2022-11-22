package exercises.x27;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.IntStream;

public class Command {
    private String text;

    public Command(String text) {
        this.text = text;
    }

    public void operation() {
        System.out.println(text);
    }
}

class Producer {
    private Queue<Command> queue = new ArrayDeque<>();
    public Queue<Command> produce(int number) {
        IntStream.range(0, number)
                .forEach(i -> queue.offer(new Command(String.valueOf(i))));
        return queue;
    }
}

class Consumer {
    private Queue<Command> queue;

    public Consumer(Queue<Command> queue) {
        this.queue = queue;
    }

    public void consume() {
        queue.stream().forEach(Command::operation);
    }
}

class Runner {
    public static void main(String[] args) {
        new Consumer(new Producer().produce(10)).consume();
    }
}
