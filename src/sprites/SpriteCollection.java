package sprites;

import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * A collection class to manage and interact with a group of sprites.
 */
public class SpriteCollection {
    private final List<Sprite> sprites; // List to hold all sprites

    /**
     * Create an empty sprites.SpriteCollection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param sprite = the sprite to be added
     */
    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    /**
     * Remove a sprite from the collection.
     *
     * @param sprite = the sprite to be removed
     */
    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    /**
     * Notifies all sprites in the collection that time has passed.
     * This method typically updates the state of each sprite.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(sprites);
        for (Sprite sprite : spritesCopy) {
            sprite.timePassed();
        }
    }

    /**
     * Draws all sprites onto the given DrawSurface.
     *
     * @param d = the DrawSurface on which to draw the sprites
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : sprites) {
            sprite.drawOn(d);
        }
    }
}
