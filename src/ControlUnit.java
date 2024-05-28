import java.util.LinkedList;

public class ControlUnit {
    private final Tape t;
    private int location;
    private String input;

    public ControlUnit() {
        this.t = new Tape();
        this.location = 0;
    }

    public void initTape(String[] strings) {
        t.init(strings);
    }

    public LinkedList<String> getTape() {
        return t.getTape();
    }

    public void beforeRun() {
        System.out.println("Performance start");
        displayTape();
    }

    public void displayTape() {
        String theLocation = "[" + t.getElement(location) + "]";
        String beforeLocation =
                String.join(" ", t.getTape().subList(0, location));
        if (location > 0) {
            beforeLocation = " " + beforeLocation;
        }
        String afterLocation =
                String.join(" ", t.getTape().subList(location + 1, t.getTape().size())) +
                " ";
        String strTape = beforeLocation + theLocation + afterLocation;
        System.out.println(strTape);
    }

    private void locationIncrement() {
        if (location == t.getTape().size() - 1) {
            t.addLast("B");
        }
        location += 1;
    }

    private void locationDecrement() {
        if (location == 0) {
            t.addFirst("B");
        } else {
            location -= 1;
        }
    }

    // partial functions
    public void start() {
        String fiveTuple = String.format("(start, %s, init, L)", t.getElement(location));
        System.out.println(fiveTuple);
        locationDecrement();
        displayTape();
        init();
    }

    private void init() {
        String fiveTuple = "(init, B, right, +, R)";
        System.out.println(fiveTuple);
        t.setElement(location, "+");
        locationIncrement();
        displayTape();
        right();
    }

    private void right() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("*")) {
            String fiveTuple = String.format("(right, %s, right, R)", t.getElement(location));
            System.out.println(fiveTuple);
            locationIncrement();
            displayTape();
            right();
            return;
        }
        if (t.getElement(location).equals("B")) {
            String fiveTuple = "(right, B, readB, L)";
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            readB();
        }
    }

    private void readB() {
        if (t.getElement(location).equals("1")) {
            String fiveTuple = "(readB, 1, addA, B, L)";
            System.out.println(fiveTuple);
            t.setElement(location, "B");
            locationDecrement();
            displayTape();
            addA();
            return;
        }
        if (t.getElement(location).equals("0")) {
            String fiveTuple = "(readB, 0, doubleL, B, L)";
            System.out.println(fiveTuple);
            t.setElement(location, "B");
            locationDecrement();
            displayTape();
            doubleL();
        }
    }

    private void doubleL() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(doubleL, %s, doubleL, L)", t.getElement(location));
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            doubleL();
            return;
        }
        if (t.getElement(location).equals("*")) {
            String fiveTuple = String.format("(doubleL, %s, shift, 0, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "0");
            locationIncrement();
            displayTape();
            shift();
        }
    }

    private void addA() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(addA, %s, addA, L)", t.getElement(location));
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            addA();
            return;
        }
        if (t.getElement(location).equals("*")) {
            String fiveTuple = "(addA, *, read, L)";
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            read();
        }
    }

    private void read() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = "(read, 0, have0, c, L)";
            System.out.println(fiveTuple);
            t.setElement(location, "c");
            locationDecrement();
            displayTape();
            have0();
            return;
        }
        if (t.getElement(location).equals("1")) {
            String fiveTuple = "(read, 1, have1, c, L)";
            System.out.println(fiveTuple);
            t.setElement(location, "c");
            locationDecrement();
            displayTape();
            have1();
            return;
        }
        if (t.getElement(location).equals("+")) {
            String fiveTuple = "(read, +, rewrite, L)";
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            rewrite();
        }
    }

    private void rewrite() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(rewrite, %s, rewrite, L)", t.getElement(location));
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            rewrite();
            return;
        }
        if (t.getElement(location).equals("I")) {
            String fiveTuple = String.format("(rewrite, %s, rewrite, 1, L)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "1");
            locationDecrement();
            displayTape();
            rewrite();
            return;
        }
        if (t.getElement(location).equals("O")) {
            String fiveTuple = String.format("(rewrite, %s, rewrite, 0, L)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "0");
            locationDecrement();
            displayTape();
            rewrite();
            return;
        }
        if (t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(rewrite, %s, double, R)", t.getElement(location));
            System.out.println(fiveTuple);
            locationIncrement();
            displayTape();
            doublee();
        }
    }

    private void have1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(have1, %s, have1, L)", t.getElement(location));
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            have1();
            return;
        }
        if (t.getElement(location).equals("+")) {
            String fiveTuple = "(have1, +, add1, L)";
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            add1();
        }
    }

    private void add1() {
        if (
                t.getElement(location).equals("O") ||
                t.getElement(location).equals("I")) {
            String fiveTuple = String.format("(add1, %s, add1, L)", t.getElement(location));
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            add1();
            return;
        }
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(add1, %s, back1, I, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "I");
            locationIncrement();
            displayTape();
            back1();
            return;
        }
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(add1, %s, carry, O, L)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "O");
            locationDecrement();
            displayTape();
            carry();
        }
    }

    private void carry() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(carry, %s, back1, 1, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "1");
            locationDecrement();
            displayTape();
            back1();
        }
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(carry, %s, carry, 0, L)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "0");
            locationDecrement();
            displayTape();
            carry();
        }
    }

    private void back1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("O") ||
                t.getElement(location).equals("I") ||
                t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(back1, %s, back1, R)", t.getElement(location));
            System.out.println(fiveTuple);
            locationIncrement();
            displayTape();
            back1();
            return;
        }
        if (t.getElement(location).equals("c")) {
            String fiveTuple = String.format("(back1, %s, read, 1, L)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "1");
            locationDecrement();
            displayTape();
            read();
        }
    }

    private void have0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(have0, %s, have0, L)", t.getElement(location));
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            have0();
            return;
        }
        if (t.getElement(location).equals("+")) {
            String fiveTuple = "(have0, +, add0, L)";
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            add0();
        }
    }

    private void add0() {
        if (
                t.getElement(location).equals("O") ||
                t.getElement(location).equals("I")) {
            String fiveTuple = String.format("(add0, %s, add0, L)", t.getElement(location));
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            add0();
            return;
        }
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(add0, %s, back0, O, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "O");
            locationIncrement();
            displayTape();
            back0();
            return;
        }
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(add0, %s, back0, I, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "I");
            locationIncrement();
            displayTape();
            back0();
        }
    }

    private void back0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("O") ||
                t.getElement(location).equals("I") ||
                t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(back0, %s, back0, R)", t.getElement(location));
            System.out.println(fiveTuple);
            locationIncrement();
            displayTape();
            back0();
            return;
        }
        if (t.getElement(location).equals("c")) {
            String fiveTuple = String.format("(back0, %s, read, 0, L)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "0");
            locationDecrement();
            displayTape();
            read();
        }
    }

    private void doublee() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(double, %s, double, R)", t.getElement(location));
            System.out.println(fiveTuple);
            locationIncrement();
            displayTape();
            doublee();
            return;
        }
        if (t.getElement(location).equals("*")) {
            String fiveTuple = String.format("(double, %s, shift, 0, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "0");
            locationIncrement();
            displayTape();
            shift();
        }
    }

    private void shift() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(shift, %s, shift0, *, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "*");
            locationIncrement();
            displayTape();
            shift0();
            return;
        }
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(shift, %s, shift1, *, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "*");
            locationIncrement();
            displayTape();
            shift1();
            return;
        }
        if (t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(shift, %s, tidy, L)", t.getElement(location));
            System.out.println(fiveTuple);
            locationDecrement();
            displayTape();
            tidyv0();
        }
    }

    private void tidyv0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(tidy, %s, tidy, B, L)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "B");
            locationDecrement();
            displayTape();
            tidyv0();
            return;
        }
        if (t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(tidy, %s, done, B, L)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "B");
            locationDecrement();
            displayTape();
            done();
        }
    }

    private void done() {
        System.out.println("Final result");
        displayTape();
    }

    private void shift1() {
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(shift1, %s, shift1, R)", t.getElement(location));
            System.out.println(fiveTuple);
            locationIncrement();
            displayTape();
            shift1();
            return;
        }
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(shift1, %s, shift0, 1, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "1");
            locationIncrement();
            displayTape();
            shift0();
            return;
        }
        if (t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(shift1, %s, right, 1, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "1");
            locationIncrement();
            displayTape();
            right();
        }
    }

    private void shift0() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(shift0, %s, shift0, R)", t.getElement(location));
            System.out.println(fiveTuple);
            locationIncrement();
            displayTape();
            shift0();
            return;
        }
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(shift0, %s, shift1, 0, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "0");
            locationIncrement();
            displayTape();
            shift1();
            return;
        }
        if (t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(shift1, %s, right, 0, R)", t.getElement(location));
            System.out.println(fiveTuple);
            t.setElement(location, "0");
            locationIncrement();
            displayTape();
            right();
        }
    }
}
