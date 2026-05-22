package listeners;

import sprites.Ball;
import sprites.Block;

/**
 * The listeners.ScoreTrackingListener class tracks scores when blocks are hit.
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;

    /**
     * Create listeners.ScoreTrackingListener.
     *
     * @param scoreCounter = the counter holding the current score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Updates the score when a block is hit by a ball.
     *
     * @param beingHit = the block that is being hit
     * @param hitter = the ball that hits the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!beingHit.ballColorMatch(hitter)) {
            currentScore.increase(5); // Increase score by 5 points
            //System.out.println("Score: " + currentScore.getValue());
        }
    }
}
