package exercises.x13;

import exercises.x13.controller.Event;

public class GreenhouseController {

    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        GreenhouseControls gc = new GreenhouseControls();
        gc.setResult(result);
        // Instead of using code, you could parse
        // configuration information from a text file:
        gc.addEvent(gc.new Bell(40));
        Event[] eventList = {
                gc.new ThermostatNight(0),
                gc.new LightOn(0),
                gc.new LightOff(0),
                gc.new WaterOn(0),
                gc.new WaterOff(0),
                gc.new ThermostatDay(0)
        };
        gc.addEvent(gc.new Restart(50, eventList));
        gc.addEvent(
                new GreenhouseControls.Terminate(100, result));
        gc.run();
    }

}
