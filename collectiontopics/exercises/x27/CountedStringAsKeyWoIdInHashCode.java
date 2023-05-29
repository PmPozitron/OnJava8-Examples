package exercises.x27;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CountedStringAsKeyWoIdInHashCode {

    private static List<String> created =
        new ArrayList<>();
    private String s;
    private int id = 0;
    public CountedStringAsKeyWoIdInHashCode(String str) {
        s = str;
        created.add(s);
        // id is the total number of instances
        // of this String used by CountedStringAsKeyWoIdInHashCode:
        for(String s2 : created)
            if(s2.equals(s))
                id++;
    }
    @Override public String toString() {
        return "String: " + s + " id: " + id +
            " hashCode(): " + hashCode();
    }
    @Override public int hashCode() {
        // The very simple approach:
        // return s.hashCode() * id;
        // Using Joshua Bloch's recipe:
        int result = 17;
        result = 37 * result + s.hashCode();
        // result = 37 * result + id;
        return result;
    }
    @Override public boolean equals(Object o) {
        return o instanceof CountedStringAsKeyWoIdInHashCode &&
            Objects.equals(s, ((CountedStringAsKeyWoIdInHashCode)o).s) &&
            Objects.equals(id, ((CountedStringAsKeyWoIdInHashCode)o).id);
    }
    public static void main(String[] args) {
        Map<CountedStringAsKeyWoIdInHashCode,Integer> map = new HashMap<>();
        CountedStringAsKeyWoIdInHashCode[] cs = new CountedStringAsKeyWoIdInHashCode[5];
        for(int i = 0; i < cs.length; i++) {
            cs[i] = new CountedStringAsKeyWoIdInHashCode("hi");
            map.put(cs[i], i); // Autobox int to Integer
        }
        System.out.println(map);
        for(CountedStringAsKeyWoIdInHashCode cstring : cs) {
            System.out.println("Looking up " + cstring);
            System.out.println(map.get(cstring));
        }
    }
}
