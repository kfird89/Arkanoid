package sprites;

import biuoop.DrawSurface;
import gameSettings.Game;

/**
 * This interface represents a game object that can be drawn on a DrawSurface,
 * update its state over time, and be added to a game environment.
 */
public interface Sprite {

    /**
     * Draws the sprite on the given DrawSurface.
     *
     * @param d = the DrawSurface on which the sprite is drawn
     */
    void drawOn(DrawSurface d);

    /**
     * Updates the state of the sprite as time passes.
     * This method is called in a game loop to update
     * the moving animation of the sprite.
     */
    void timePassed();

    /**
     * Adds the sprite to the specified game.
     *
     * @param game = the game to which the sprite is added
     */
    void addToGame(Game game);

}
