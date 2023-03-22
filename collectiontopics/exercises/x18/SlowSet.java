package exercises.x18;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SlowSet<T> extends AbstractSet<T> {

    private List<T> values = new ArrayList<>();

    @Override
    public boolean add(T t) {
        if (values.contains(t)) {
            return false;
        }
        return values.add(t);
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    @Override
    public int size() {
        return values.size();
    }
}

class TestDrive {
    public static void main(String[] args) {
        SlowSet slowSet = new SlowSet();
        slowSet.addAll(onjava.Countries.names());
        System.out.println(slowSet.size());
        slowSet.addAll(onjava.Countries.names());
        System.out.println(slowSet.size());
        String country = onjava.Countries.names().get(0);
        System.out.println(country);
        slowSet.remove(country);
        System.out.println(slowSet.size());

        System.out.println(slowSet.add(country));
        System.out.println(slowSet.add(country));

        System.out.println(slowSet.removeAll(List.of(onjava.Countries.names().get(0), onjava.Countries.names().get(1))));
        System.out.println(slowSet.size());


    }
}
