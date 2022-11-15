package exercises.x7;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Seven {
    private static int COUNTER = 0;

    private static Seven[] INSTANCES = new Seven[]{new Seven(), new Seven(), new Seven(), new Seven(), new Seven(), new Seven(), new Seven()};
    private int id = COUNTER++;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seven)) return false;
        Seven seven = (Seven) o;
        return id == seven.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Seven.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(INSTANCES));

        List<Seven> theList = new ArrayList<>(INSTANCES.length);
        Collections.addAll(theList, INSTANCES);
        System.out.println(theList);
        Collections.shuffle(theList);
        System.out.println(theList);
        System.out.println(Arrays.toString(INSTANCES));
        List<Seven> sub = theList.subList(0, 3);
        System.out.println(sub);
        List<Seven> copy = new ArrayList<>(sub);
        theList.removeAll(sub);
        System.out.println(theList);
        System.out.println(copy);

    }
}
