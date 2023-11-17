package exercises.x40_x41_x42;

public class TwoStringsSecond extends TwoStringsFirst{
    @Override
    public int compareTo(TwoStringsFirst o) {
        return second.compareTo(o.second);
    }

    public TwoStringsSecond(String first, String second) {
        super(first, second);
    }
}
