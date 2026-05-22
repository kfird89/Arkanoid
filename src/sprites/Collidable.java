package sprites;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * This interface represents an object that can be collided with.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that it has been collided with at the specified point with the given velocity.
     * And returns the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param hitter = the ball that hit the current block.
     * @param collisionPoint = the point at which the collision occurred
     * @param currentVelocity = the current velocity of the object before the collision
     * @return the new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
