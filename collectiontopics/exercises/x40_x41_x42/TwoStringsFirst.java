package exercises.x40_x41_x42;

import java.util.Objects;
import java.util.StringJoiner;

public class TwoStringsFirst implements Comparable<TwoStringsFirst>{

    protected String first;
    protected String second;

    public TwoStringsFirst(String first, String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(TwoStringsFirst o) {
        return first.compareTo(o.first);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwoStringsFirst)) return false;
        TwoStringsFirst that = (TwoStringsFirst) o;
        return Objects.equals(first, that.first) && Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return new StringJoiner("")
                .add(first + ":")
                .add(second)
                .toString();
    }
}
