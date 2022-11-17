// innerclasses/controller/Controller.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// The reusable framework for control systems
package exercises.x13.controller;

import exercises.x13.GreenhouseControls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class Controller {
    private StringBuilder result;
    // A class from java.util to hold Event objects:
    private List<Event> eventList = new LinkedList<>();

    public Controller setResult(StringBuilder result) {
        this.result = result;
        return this;
    }

    public void addEvent(Event c) {
        eventList.add(c);
    }

    public void clearAllButRestartAndBell() {
        LinkedList<Event> copy = new LinkedList<>(eventList);
        Event bell = copy.stream()
                .filter(item -> item instanceof GreenhouseControls.Bell)
                .findFirst()
                .get();

        eventList = new LinkedList<>(Arrays.asList(bell));
        copy.stream()
                .filter(item -> item instanceof GreenhouseControls.Terminate)
                .findFirst()
                .ifPresent(this::addEvent);
    }

    public void run() {
        while (true) {
            LinkedList<Event> copy = new LinkedList<>(eventList);
            Iterator<Event> iterator = copy.iterator();
            while (iterator.hasNext()) {
                Event e = iterator.next();
                if (e.ready()) {
                    result
                            .append(e)
                            .append("\n");
                    e.action();
                    iterator.remove();
                }
            }
        }
    }
}
