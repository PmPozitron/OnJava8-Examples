package exercises.x26;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CountedStringWithChar {

    private static List<String> created = new ArrayList<>();
    private String s;
    private int id = 0;

    private char ch;
    public CountedStringWithChar(String str) {
        ch = '0';
        s = str;
        created.add(s);
        // id is the total number of instances
        // of this String used by CountedStringWithChar:
        for(String s2 : created)
            if(s2.equals(s))
                id++;
    }
    @Override public String toString() {
        return "String: " + s + " id: " + id + " char: " + ch + " hashCode(): " + hashCode();
    }
    @Override public int hashCode() {
        // The very simple approach:
        // return s.hashCode() * id;
        // Using Joshua Bloch's recipe:
        int result = 17;
        result = 37 * result + s.hashCode();
        result = 37 * result + id;
        result = 37 * result + ch;

        return result;
    }
    @Override public boolean equals(Object o) {
        return o instanceof CountedStringWithChar &&
            Objects.equals(s, ((CountedStringWithChar)o).s) &&
            Objects.equals(id, ((CountedStringWithChar)o).id) &&
            Objects.equals(ch, ((CountedStringWithChar)o).ch);
    }
    public static void main(String[] args) {
        Map<CountedStringWithChar,Integer> map = new HashMap<>();
        CountedStringWithChar[] cs = new CountedStringWithChar[5];
        for(int i = 0; i < cs.length; i++) {
            cs[i] = new CountedStringWithChar("hi");
            map.put(cs[i], i); // Autobox int to Integer
        }
        System.out.println(map);
        for(CountedStringWithChar cstring : cs) {
            System.out.println("Looking up " + cstring);
            System.out.println(map.get(cstring));
        }
    }
}
