package listeners;

import gameSettings.Game;

import sprites.Ball;
import sprites.Block;

/**
 * A listeners.BlockRemover is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private final Game game;
    private Counter remainingBlocks;

    /**
     * Create a listeners.BlockRemover.
     *
     * @param game = the game from which blocks are removed
     * @param remainingBlocks = the counter for the remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Remove Blocks that got hit from the game.
     *
     * @param beingHit = the block that is being hit
     * @param hitter = the ball that hits the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!beingHit.ballColorMatch(hitter)) {
            beingHit.removeFromGame(game);
            beingHit.removeHitListener(this);
            remainingBlocks.decrease(1);
            hitter.setColor(beingHit.getColor());
            //System.out.println("sprites.Block removed. Remaining blocks: " + remainingBlocks.getValue());
        }
    }
}
