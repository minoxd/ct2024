package pkg;

public class TuringMachineOld {
    private final ControlUnitOld cu;
    public TuringMachineOld() {
        this.cu = new ControlUnitOld();
    }

    public static void main(String[] args) {
        // initialize a Man
        System.out.println("Initializing program...");
        System.out.println("""
                Previously in the paper question,\s
                I wrote that such turing machine that can compute exponentiation does not exist.\s
                Now i am here to prove my self wrong.""");
        TuringMachineOld tm = new TuringMachineOld();

        try {
            String[] tape = {"1", "1", "^", "1", "0"};
            tm.cu.initTape(tape);
            tm.perform();

            // end
            System.out.println("\nGood bye.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void perform() {
        cu.beforeRun();
        cu.startExp();
        cu.printSteps();
    }
}
