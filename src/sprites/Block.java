package sprites;

import biuoop.DrawSurface;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import gameSettings.Game;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import listeners.HitListener;
import listeners.HitNotifier;

/**
 * This class create a 2d block in the game that can be collided with.
 * It implements the sprites.Collidable and sprites.Sprite interfaces.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private final Rectangle rectangle;
    private final Color color;
    private final List<HitListener> hitListeners;



    /**
     * Create a sprites.Block with specified upper-left corner, width, height, and color.
     *
     * @param upperLeft = the upper-left corner of the block
     * @param width     = the width of the block
     * @param height    = the height of the block
     * @param color     = the color of the block
     */
    public Block(Point upperLeft, int width, int height, Color color) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * @return the "collision shape" of the block
     */
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    /**
     * @return the color of the block
     */
    public Color getColor() {
        return this.color;
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }


    /**
     * Notifies the block that it has been collided with at the specified point with the given velocity.
     * And notify the blockRemover that a ball hit this block.
     * The method returns the new velocity expected after the hit (based on the force the block inflicted).
     *
     * @param hitter = the ball that hit the current block
     * @param collisionPoint  = the point at which the collision occurred
     * @param currentVelocity = the current velocity of the object before the collision
     * @return the new velocity expected after the hit
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // If the collision happens at the top or bottom of the block,
        // reverse the vertical direction of the velocity
        if (collisionPoint.getY() == rectangle.getUpperLeft().getY()
                || collisionPoint.getY() == rectangle.getUpperLeft().getY() + rectangle.getHeight()) {
            dy = -dy;
        }

        // If the collision happens at the left or right side of the block,
        // reverse the horizontal direction of the velocity
        if (collisionPoint.getX() == rectangle.getUpperLeft().getX()
                || collisionPoint.getX() == rectangle.getUpperLeft().getX() + rectangle.getWidth()) {
            dx = -dx;
        }

        this.notifyHit(hitter);

        return new Velocity(dx, dy);
    }

    /**
     * Draws the block on the given draw surface.
     *
     * @param d = the draw surface to draw the block on
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Fill the rectangle with the given color
        d.setColor(color);
        d.fillRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());

        // Set the color to black for the border
        d.setColor(Color.BLACK);
        // Draw the border of the rectangle
        d.drawRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    /**
     * Updates the state of the block as time passes.
     * Currently, this method does not perform any actions for the block.
     * But must add it cause the Implementation.
     */
    @Override
    public void timePassed() {
        // Additional behavior for blocks if needed
    }

    /**
     * Adds the block to the game as both a collidable and a sprite.
     *
     * @param game = the game to add the block to
     */
    @Override
    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * check if the ball and the block have the same color.
     *
     * @param ball = the ball to check if he in the same color of the block
     * @return true if they have the same color, false if they not.
     */
    public boolean ballColorMatch(Ball ball) {
        return ball.getColor().equals(this.color);

    }

    /**
     * remove the block from the game.
     *
     * @param game = the game to remove the block from.
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);

    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);

    }
}
