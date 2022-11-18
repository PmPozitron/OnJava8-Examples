package exercises.x15;

import onjava.Stack;

public class Evaluator {
    private static final String EXPRESSION = "+U+n+c---+e+r+t---+a-+i-+n+t+y---+ -+r+u--+l+e+s---";
    private static Stack stack = new Stack();

    public static void main(String[] args) {
        evaluate(EXPRESSION);
    }

    //cnUtreaiytn ursel
    //cnUtreaiytn ursel
    private static void evaluate(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '+') {
                stack.push(string.charAt(++i));

            } else if (string.charAt(i) == '-') {
                System.out.print(stack.pop());
            }
        }

    }
}
