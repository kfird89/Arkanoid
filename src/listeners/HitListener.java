package listeners;

import sprites.Ball;
import sprites.Block;

/**
 * The listeners.HitListener interface represents an object that listens for hit events.
 * Implementing classes can define actions to be taken when a hit event occurs.
 */
public interface HitListener {

    /**
     * Called when the beingHit object is hit by the hitter sprites.Ball.
     *
     * @param beingHit = the block that is being hit
     * @param hitter = the ball that hits the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}