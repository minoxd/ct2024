import java.util.Arrays;
import java.util.Scanner;

public class TuringMachine {
    private ControlUnit cu;

    private static final Scanner inputScanner = new Scanner(System.in);
    private final static String[] EmptyArr = {};

    public TuringMachine() {
        this.cu = new ControlUnit();
    }

    public static void main(String[] args) {
        // initialize a Man
        System.out.println("Initializing program...");
        TuringMachine tm = new TuringMachine();

        try {
//            tm.init();
//            String[] example = {"1", "1", "*", "1", "0", "1"};
            String[] example = {"1", "1", "*", "1", "1", "0"};
            tm.cu.initTape(example);
            // TODO: perform exponent
            tm.perform();

            // end
            System.out.println("\nGood bye.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void init() {
        System.out.println("\n* Initializing tape *\n");

        String[] keywords;
        do {
            //   prompt the user to enter one or more keywords (separated by spaces)
            keywords = promptForKeywords();

            if (keywords != null && keywords.length > 0) { // keywords were entered
                System.out.println("\nAdding to tape: " + Arrays.toString(keywords));

                //   invoke operation init(String[]) to search using these keywords
                try {
                    cu.initTape(keywords);
                    //   print the tape to the standard output
                    display();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } while (keywords != null);
    }

    private void perform() {

        cu.beforeRun();
        cu.start();
    }

    private String[] promptForKeywords() {
        System.out.println("Enter some keywords (separated by spaces) or 'C' to cancel: ");
        String s = inputScanner.nextLine();
        s = s.trim();
        if (s.isEmpty()) {
            return EmptyArr;
        } if (s.equalsIgnoreCase("C")) {
            return null;
        } else {
            return s.split(" ");
        }
    }

    private void display() {
        System.out.println(cu.getTape());
    }
}
