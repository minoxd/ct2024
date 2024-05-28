import java.util.Arrays;
import java.util.LinkedList;

public class Tape {
    private final LinkedList<String> tape;

    public Tape() {
        this.tape = new LinkedList<>();
    }

    public void init(String[] strings) {
        tape.addAll(Arrays.asList(strings));
    }

    public LinkedList<String> getTape() {
        return tape;
    }

    public String getElement(int index) {
        return tape.get(index);
    }

    public void setElement(int index, String string) {
        tape.set(index, string);
    }

    public void addFirst(String string) {
        tape.addFirst(string);
    }

    public void addLast(String string) {
        tape.addLast(string);
    }
}
