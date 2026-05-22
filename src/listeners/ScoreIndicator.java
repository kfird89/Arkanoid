package listeners;

import biuoop.DrawSurface;
import java.awt.Color;

import gameSettings.Game;

import sprites.Sprite;

/**
 * The listeners.ScoreIndicator class displays the current score on a DrawSurface.
 */
public class ScoreIndicator implements Sprite {
    private final Counter scoreCounter;

    /**
     * Create listeners.ScoreIndicator.
     *
     * @param scoreCounter = the counter holding the current score
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    /**
     * Draws the score indicator on the given DrawSurface.
     *
     * @param d = the DrawSurface on which to draw the score
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(370, 15, "Score: " + scoreCounter.getValue(), 15);
    }

    /**
     * Currently, listeners.ScoreIndicator does not change over time.
     */
    @Override
    public void timePassed() {
        // Currently listeners.ScoreIndicator does not change with time
    }

    /**
     * Adds this listeners.ScoreIndicator sprite to the given game.
     *
     * @param game = the game to which this sprite should be added
     */
    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
