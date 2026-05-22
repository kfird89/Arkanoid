package listeners;
import gameSettings.Game;

import sprites.Ball;
import sprites.Block;

/**
 * A listeners.BallRemover is responsible for removing balls from the game
 * and keeping track of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    private final Game game;
    private Counter remainingBalls;

    /**
     * Create a listeners.BallRemover.
     *
     * @param game = the game from which balls are removed
     * @param remainingBalls = the counter for the remaining balls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Remove balls that hit the dead region block from the game.
     *
     * @param beingHit = the block that is being hit
     * @param hitter = the ball that hits the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        remainingBalls.decrease(1);
        hitter.removeFromGame(game);
        //System.out.println("sprites.Ball removed. Remaining Balls: " + remainingBalls.getValue());
    }
}
