package listeners;

/**
 * The listeners.HitNotifier interface represents an object that can notify listeners
 * about hit events.
 */
public interface HitNotifier {

    /**
     * Adds a listeners.HitListener to be notified of hit events.
     *
     * @param hl = the listeners.HitListener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a listeners.HitListener from the list of listeners for hit events.
     *
     * @param hl = the listeners.HitListener to remove
     */
    void removeHitListener(HitListener hl);
}
