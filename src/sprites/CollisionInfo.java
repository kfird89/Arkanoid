package sprites;

import geometry.Point;

/**
 * This class represents information about a collision between a specific point and a collidable object.
 */
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * Create a new sprites.CollisionInfo object with the given collision point and collidable object.
     *
     * @param collisionPoint = the point at which the collision occurred
     * @param collisionObject = the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * @return the collision point
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}
