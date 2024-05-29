package pkg;

import java.util.LinkedList;

public class ControlUnitOld {
    private final TapeOld t;
    private int location;
    private final StringBuilder runningSteps;

    public ControlUnitOld() {
        this.t = new TapeOld();
        this.location = 0;
        this.runningSteps = new StringBuilder();
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

    public String displayTape() {
        String theLocation = "\u001B[34m" + "[" + t.getElement(location) + "]" + "\u001B[0m";
        String beforeLocation =
                String.join(" ", t.getTape().subList(0, location));
        if (location > 0) {
            beforeLocation = " " + beforeLocation;
        }
        String afterLocation =
                String.join(" ", t.getTape().subList(location + 1, t.getTape().size())) +
                " ";
        return beforeLocation + theLocation + afterLocation + "\n";
    }

    private void locationIncrement() {
        if (location == t.getTape().size() - 1) {
            t.addLast(" ");
        }
        location += 1;
    }

    private void locationDecrement() {
        if (location == 0) {
            t.addFirst(" ");
        } else {
            location -= 1;
        }
    }

    public void printSteps() {
        String[] steps = runningSteps.toString().split("\n");
        for (int i = 0; i < steps.length / 2; i++) {
            System.out.println("Step " + i + ": " + steps[2*i] + "\n" + steps[2*i+1]);
        }
        System.out.println(steps[steps.length - 1]);
    }

    // partial functions
    public void startExp() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(startExp, %s, initv1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            initv1();
        }
    }

    private void initv1() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(initv1, %s, initv2, |, L)\n", t.getElement(location));
            t.setElement(location, "|");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            initv2();
        }
    }

    private void initv2() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(initv2, %s, initv3, B, L)\n", t.getElement(location));
            t.setElement(location, "B");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            initv3();
        }
    }

    private void initv3() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(initv3, %s, initv4, 1, L)\n", t.getElement(location));
            t.setElement(location, "1");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            initv4();
        }
    }

    private void initv4() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(initv4, %s, rightv1, *, R)\n", t.getElement(location));
            t.setElement(location, "*");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv1();
        }
    }

    private void rightv1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("*") ||
                t.getElement(location).equals("|") ||
                t.getElement(location).equals("B") ||
                t.getElement(location).equals("^")) {
            String fiveTuple = String.format("(rightv1, %s, rightv1, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv1();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(rightv1, %s, readC, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            readC();
        }
    }

    private void readC() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(readC, %s, checkFull0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            checkFull0();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(readC, %s, decrement, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            decrement();
        }
    }

    private void checkFull0() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(checkFull0, %s, checkFull0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            checkFull0();
        } else
        if (t.getElement(location).equals("^")) {
            String fiveTuple = String.format("(checkFull0, %s, rightv5, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv5();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("((checkFull0, %s, rightv4, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv4();
        }
    }

    private void rightv4() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(rightv4, %s, rightv4, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv4();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(rightv4, %s, decrement, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            decrement();
        }
    }

    private void rightv5() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(rightv5, %s, rightv5, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv5();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(rightv5, %s, tidyv1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            tidyv1();
        }
    }

    private void tidyv1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("^") ||
                t.getElement(location).equals("|")) {
            String fiveTuple = String.format("(tidyv1, %s, tidyv1,  , L)\n", t.getElement(location));
            t.setElement(location, " ");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            tidyv1();
        } else
        if (t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(tidyv1, %s, tidyv2,  , L)\n", t.getElement(location));
            t.setElement(location, " ");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            tidyv2();
        }
    }

    private void tidyv2() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(tidyv2, %s, tidyv2, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            tidyv2();
        } else
        if (t.getElement(location).equals("*")) {
            String fiveTuple = String.format("(tidyv2, %s, doneExp,  , R)\n", t.getElement(location));
            t.setElement(location, " ");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            doneExp();
        }
    }

    private void doneExp() {
        runningSteps.append("Final result: ").append(displayTape().strip());
    }

    private void decrement() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(decrement, %s, decrement, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            decrement();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(decrement, %s, leftv1, 0, L)\n", t.getElement(location));
            t.setElement(location, "0");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            leftv1();
        } else
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(decrement, %s, decrement, 1, L)\n", t.getElement(location));
            t.setElement(location, "1");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            decrement();
        } else
        if (t.getElement(location).equals("^")) {
            String fiveTuple = String.format("(decrement, %s, leftv1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            leftv1();
        }
    }

    private void leftv1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(leftv1, %s, leftv1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            leftv1();
        } else
        if (t.getElement(location).equals("^")) {
            String fiveTuple = String.format("(leftv1, %s, duplicateX, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            duplicateX();
        }
    }

    private void duplicateX() {
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(duplicateX, %s, dup1, _, L)\n", t.getElement(location));
            t.setElement(location, "_");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            dup1();
        } else
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(duplicateX, %s, dup0, _, L)\n", t.getElement(location));
            t.setElement(location, "_");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            dup0();
        }
    }

    private void dup1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("|") ||
                t.getElement(location).equals("*") ||
                t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(dup1, %s, dup1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            dup1();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(dup1, %s, backDup1, 1, R)\n", t.getElement(location));
            t.setElement(location, "1");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backDup1();
        }
    }

    private void dup0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("|") ||
                t.getElement(location).equals("*") ||
                t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(dup0, %s, dup0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            dup0();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(dup0, %s, backDup0, 0, R)\n", t.getElement(location));
            t.setElement(location, "0");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backDup0();
        }
    }

    private void backDup1() {
        if (t.getElement(location).equals("_")) {
            String fiveTuple = String.format("(backDup1, %s, duplicateX, 1, L)\n", t.getElement(location));
            t.setElement(location, "1");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            duplicateX();
        } else
        if (
                t.getElement(location).equals("0") ||
                    t.getElement(location).equals("1") ||
                    t.getElement(location).equals("*") ||
                    t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(backDup1, %s, backDup1, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backDup1();
        } else
        if (t.getElement(location).equals("|")) {
            String fiveTuple = String.format("(backDup1, %s, checkDoneDupX1, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            checkDoneDupX1();
        }
    }

    private void backDup0() {
        if (t.getElement(location).equals("_")) {
            String fiveTuple = String.format("(backDup0, %s, duplicateX, 0, L)\n", t.getElement(location));
            t.setElement(location, "0");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            duplicateX();
        } else
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("*") ||
                t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(backDup0, %s, backDup0, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backDup0();
        } else
        if (t.getElement(location).equals("|")) {
            String fiveTuple = String.format("(backDup0, %s, checkDoneDupX0, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            checkDoneDupX0();
        }
    }

    private void checkDoneDupX1() {
        if (t.getElement(location).equals("_")) {
            String fiveTuple = String.format("(checkDoneDupX1, %s, leftv2, 1, L)\n", t.getElement(location));
            t.setElement(location, "1");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            leftv2();
        } else
        if (
                t.getElement(location).equals("0") ||
                        t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(checkDoneDupX1, %s, backDup1, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backDup1();
        }
    }

    private void checkDoneDupX0() {
        if (t.getElement(location).equals("_")) {
            String fiveTuple = String.format("(checkDoneDupX0, %s, leftv2, 0, L)\n", t.getElement(location));
            t.setElement(location, "0");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            leftv2();
        } else
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(checkDoneDupX0, %s, backDup0, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backDup0();
        }
    }

    private void leftv2() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("*") ||
                t.getElement(location).equals("|") ||
                t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(leftv2, %s, leftv2, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            leftv2();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(leftv2, %s, startMult, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            startMult();
        }
    }

    private void doneMult() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(doneMult, %s, leftv3, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            leftv3();
        }
    }

    private void leftv3() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(leftv3, %s, leftv3, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            leftv3();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(leftv3, %s, rightv2, s, R)\n", t.getElement(location));
            t.setElement(location, "s");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv2();
        }
    }

    private void rightv2() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(rightv2, %s, rightv2, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv2();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(rightv2, %s, shiftAns, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shiftAns();
        } else
        if (t.getElement(location).equals("b")) {
            String fiveTuple = String.format("(rightv2, %s, rightv1, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv1();
        }
    }

    private void shiftAns() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(shiftAns, %s, shiftAns0, b, R)\n", t.getElement(location));
            t.setElement(location, "b");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shiftAns0();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(shiftAns, %s, shiftAns1, b, R)\n", t.getElement(location));
            t.setElement(location, "b");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shiftAns1();
        } else
        if (t.getElement(location).equals("s")) {
            String fiveTuple = String.format("(shiftAns, %s, rightv3,  , R)\n", t.getElement(location));
            t.setElement(location, " ");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv3();
        }
    }

    private void rightv3() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(rightv3, %s, rightv3, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv3();
        } else
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(rightv3, %s, initv4, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            initv4();
        }
    }

    private void shiftAns0() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(shiftAns0, %s, shiftAns0, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shiftAns0();
        } else
        if (
                t.getElement(location).equals("0") ||
                        t.getElement(location).equals("1") ||
                        t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(shiftAns0, %s, back1ShiftAns0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            back1ShiftAns0();
        }
    }

    private void shiftAns1() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(shiftAns1, %s, shiftAns1, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shiftAns1();
        } else
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(shiftAns1, %s, back1ShiftAns1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            back1ShiftAns1();
        }
    }

    private void back1ShiftAns0() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(back1ShiftAns0, %s, backShiftAns, 0, L)\n", t.getElement(location));
            t.setElement(location, "0");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backShiftAns();
        }
    }

    private void back1ShiftAns1() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(back1ShiftAns1, %s, backShiftAns, 1, L)\n", t.getElement(location));
            t.setElement(location, "1");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backShiftAns();
        }
    }

    private void backShiftAns() {
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(backShiftAns, %s, backShiftAns, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            backShiftAns();
        } else
        if (t.getElement(location).equals("b")) {
            String fiveTuple = String.format("(backShiftAns, %s, shiftAns,  , L)\n", t.getElement(location));
            t.setElement(location, " ");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shiftAns();
        } else
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(backShiftAns, %s, rightv2, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv2();
        }
    }

    private void startMult() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(startMult, %s, initv0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            initv0();
        }
    }

    private void initv0() {
        if (
                t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(initv0, %s, rightv0, +, R)\n", t.getElement(location));
            t.setElement(location, "+");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv0();
        }
    }

    private void rightv0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("*")) {
            String fiveTuple = String.format("(rightv0, %s, rightv0, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv0();
        } else
        if (t.getElement(location).equals("B")) {
            String fiveTuple = String.format("(rightv0, %s, readB, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            readB();
        }
    }

    private void readB() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(readB, %s, doubleL,  , L)\n", t.getElement(location));
            t.setElement(location, " ");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            doubleL();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(readB, %s, addA,  , L)\n", t.getElement(location));
            t.setElement(location, " ");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            addA();
        }
    }

    private void addA() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(addA, %s, addA, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            addA();
        } else
        if (t.getElement(location).equals("*")) {
            String fiveTuple = String.format("(addA, %s, read, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            read();
        }
    }

    private void doubleL() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(doubleL, %s, doubleL, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            doubleL();
        } else
        if (t.getElement(location).equals("*")) {
            String fiveTuple = String.format("(doubleL, %s, shift, 0, R)\n", t.getElement(location));
            t.setElement(location, "0");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shift();
        }
    }

    private void doublee() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(double, %s, double, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            doublee();
            return;
        } else
        if (t.getElement(location).equals("*")) {
            String fiveTuple = String.format("(double, %s, shift, 0, R)\n", t.getElement(location));
            t.setElement(location, "0");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shift();
        }
    }

    private void shift() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(shift, %s, shift0, *, R)\n", t.getElement(location));
            t.setElement(location, "*");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shift0();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(shift, %s, shift1, *, R)\n", t.getElement(location));
            t.setElement(location, "*");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shift1();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(shift, %s, tidyv0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            tidyv0();
        }
    }

    private void shift0() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(shift0, %s, shift0, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shift0();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(shift0, %s, shift1, 0, R)\n", t.getElement(location));
            t.setElement(location, "0");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shift1();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(shift0, %s, rightv0, 0, R)\n", t.getElement(location));
            t.setElement(location, "0");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv0();
        }
    }

    private void shift1() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(shift1, %s, shift0, 1, R)\n", t.getElement(location));
            t.setElement(location, "1");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shift0();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(shift1, %s, shift1, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            shift1();
        } else
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(shift1, %s, rightv0, 1, R)\n", t.getElement(location));
            t.setElement(location, "1");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rightv0();
        }
    }

    private void tidyv0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(tidyv0, %s, tidyv0,  , L)\n", t.getElement(location));
            t.setElement(location, " ");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            tidyv0();
        } else
        if (t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(tidyv0, %s, doneMult,  , L)\n", t.getElement(location));
            t.setElement(location, " ");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            doneMult();
        }
    }

    private void read() {
        if (t.getElement(location).equals("0")) {
            String fiveTuple = String.format("(read, %s, have0, c, L)\n", t.getElement(location));
            t.setElement(location, "c");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            have0();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(read, %s, have1, c, L)\n", t.getElement(location));
            t.setElement(location, "c");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            have1();
        } else
        if (t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(read, %s, rewrite, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rewrite();
        }
    }

    private void have0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(have0, %s, have0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            have0();
        } else
        if (t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(have0, %s, add0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            add0();
        }
    }

    private void have1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(have1, %s, have1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            have1();
        } else
        if (t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(have1, %s, add1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            add1();
        }
    }

    private void add0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(add0, %s, back0, O, R)\n", t.getElement(location));
            t.setElement(location, "O");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            back0();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(add0, %s, back0, I, R)\n", t.getElement(location));
            t.setElement(location, "I");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            back0();
        } else
        if (
                t.getElement(location).equals("O") ||
                t.getElement(location).equals("I")) {
            String fiveTuple = String.format("(add0, %s, add0, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            add0();
        }
    }

    private void add1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(add1, %s, back1, I, R)\n", t.getElement(location));
            t.setElement(location, "I");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            back1();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(add1, %s, carry, O, L)\n", t.getElement(location));
            t.setElement(location, "O");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            carry();
        } else
        if (
                t.getElement(location).equals("O") ||
                t.getElement(location).equals("I")) {
            String fiveTuple = String.format("(add1, %s, add1, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            add1();
        }
    }

    private void carry() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(carry, %s, back1, 1, R)\n", t.getElement(location));
            t.setElement(location, "1");
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            back1();
        } else
        if (t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(carry, %s, carry, 0, L)\n", t.getElement(location));
            t.setElement(location, "0");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            carry();
        }
    }

    private void back0() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("O") ||
                t.getElement(location).equals("I") ||
                t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(back0, %s, back0, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            back0();
        } else
        if (t.getElement(location).equals("c")) {
            String fiveTuple = String.format("(back0, %s, read, 0, L)\n", t.getElement(location));
            t.setElement(location, "0");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            read();
        }
    }

    private void back1() {
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1") ||
                t.getElement(location).equals("O") ||
                t.getElement(location).equals("I") ||
                t.getElement(location).equals("+")) {
            String fiveTuple = String.format("(back1, %s, back1, R)\n", t.getElement(location));
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            back1();
        } else
        if (t.getElement(location).equals("c")) {
            String fiveTuple = String.format("(back1, %s, read, 1, L)\n", t.getElement(location));
            t.setElement(location, "1");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            read();
        }
    }

    private void rewrite() {
        if (t.getElement(location).equals("O")) {
            String fiveTuple = String.format("(rewrite, %s, rewrite, 0, L)\n", t.getElement(location));
            t.setElement(location, "0");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rewrite();
        } else
        if (t.getElement(location).equals("I")) {
            String fiveTuple = String.format("(rewrite, %s, rewrite, 1, L)\n", t.getElement(location));
            t.setElement(location, "1");
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rewrite();
        } else
        if (
                t.getElement(location).equals("0") ||
                t.getElement(location).equals("1")) {
            String fiveTuple = String.format("(rewrite, %s, rewrite, L)\n", t.getElement(location));
            locationDecrement();
            runningSteps.append(fiveTuple).append(displayTape());
            rewrite();
        }
        if (t.getElement(location).equals(" ")) {
            String fiveTuple = String.format("(rewrite, %s, double, R)\n", t.getElement(location));;
            locationIncrement();
            runningSteps.append(fiveTuple).append(displayTape());
            doublee();
        }
    }
}
