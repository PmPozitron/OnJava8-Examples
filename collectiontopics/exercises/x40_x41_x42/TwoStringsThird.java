package exercises.x40_x41_x42;

public class TwoStringsThird extends TwoStringsFirst{

    public TwoStringsThird(String first, String second) {
        super(first, second);
    }

    @Override
    public int compareTo(TwoStringsFirst o) {
        return first.concat(second).compareTo(o.first.concat(o.second));
    }
}
