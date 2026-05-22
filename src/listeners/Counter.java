package listeners;

/**
 * A listeners.Counter class represents a simple integer counter.
 */
public class Counter {

    private int count;

    /**
     * Increases the current count by a specified number.
     *
     * @param number = the number to add to the current count
     */
    public void increase(int number) {
        count += number;
    }

    /**
     * Decreases the current count by a specified number.
     *
     * @param number = the number to subtract from the current count
     */
    public void decrease(int number) {
        count -= number;
    }

    /**
     * @return = the current value of the counter
     */
    public int getValue() {
        return count;
    }
}
