package gameSettings;

import geometry.Point;
import geometry.Line;

import sprites.Collidable;
import sprites.CollisionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages a collection of collidable objects in a game.
 * It provides methods to add collidables and find the closest collision point with a given trajectory.
 */
public class GameEnvironment {
    private final List<Collidable> collidables;

    /**
     * Create an empty game environment with an initial empty list of collidables.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Create a game environment with a specified list of collidables.
     *
     * @param collidables = the initial list of collidables
     */
    public GameEnvironment(List<Collidable> collidables) {
        this.collidables = collidables;
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c = the collidable object to add
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Remove a collidable object from the game environment.
     *
     * @param c = the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

    /**
     * Finds the closest collision between a given trajectory and any collidable object in the game environment.
     *
     * @param trajectory = the trajectory (line) for which to find the closest collision
     * @return information about the closest collision: the point of collision and the collidable object involved,
     *         or "null" if no collision occurs.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestCollidable = null;
        double closestDistance = Double.MAX_VALUE;

        // Iterate through all collidables to find the closest intersection point
        List<Collidable> collidableCopy = new ArrayList<>(collidables); // Create a copy to iterate over

        for (Collidable collidable : collidableCopy) {
            Point intersection = trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
            if (intersection != null) {
                double distance = trajectory.start().distance(intersection);
                if (distance < closestDistance) {
                    closestPoint = intersection;
                    closestCollidable = collidable;
                    closestDistance = distance;
                }
            }
        }

        // Return information about the closest collision, if found
        if (closestCollidable != null) {
            return new CollisionInfo(closestPoint, closestCollidable);
        }
        return null; // No collision found
    }
}
