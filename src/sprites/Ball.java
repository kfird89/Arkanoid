package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

import gameSettings.Game;
import gameSettings.GameEnvironment;

import geometry.Point;
import geometry.Velocity;
import geometry.Line;


/**
 * This class create a 2d sprites.Ball in a game.
 */
public class Ball implements Sprite {
    private Point center;        // The center point of the ball
    private final int radius;    // The radius of the ball
    private Color color;   // The color of the ball
    private Velocity velocity;   // The velocity of the ball
    private final GameEnvironment gameEnvironment; // The game environment to handle collisions

    /**
     * Create a new sprites.Ball with the specified center, radius, color, and game environment.
     *
     * @param center = the center point of the ball
     * @param radius = the radius of the ball
     * @param color  = the color of the ball
     * @param gameEnvironment = the game environment containing collidable objects
     */
    public Ball(Point center, int radius, Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(0, 0); // Initialize velocity to (0, 0)
        this.gameEnvironment = gameEnvironment;

    }

    /**
     * Sets the x-coordinate of the center point of the ball.
     *
     * @param x = the new x-coordinate of the center point
     */
    public void setX(double x) {
        this.center = new Point(x, getY());
    }

    /**
     * @return the x-coordinate of the center point
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the y-coordinate of the center point
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the center point of the ball
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @return the radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return the color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of the ball.
     *
     * @param color = the color of the ball
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface = the DrawSurface to draw the ball on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle(getX(), getY(), radius);
    }

    /**
     * Sets the velocity of the ball to the specified velocity.
     *
     * @param v = the new velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Moves the ball one step according to its velocity.
     * Also handles collisions with objects.
     */
    public void moveOneStep() {
        // Compute the ball trajectory considering radius and velocity
        double endX, endY;
        if (this.velocity.getDx() > 0) {
            endX = this.center.getX() + this.velocity.getDx() + radius; // Adjust for velocity and radius
        } else {
            endX = this.center.getX() + this.velocity.getDx() - radius; // Adjust for velocity and radius
        }
        if (this.velocity.getDy() > 0) {
            endY = this.center.getY() + this.velocity.getDy() + radius; // Adjust for velocity and radius
        } else {
            endY = this.center.getY() + this.velocity.getDy() - radius; // Adjust for velocity and radius
        }
        Point end = new Point(endX, endY);
        // Create the line trajectory from current center location to feature location + radius
        Line trajectory = new Line(this.center, end);

        // 2) Find the closest intersection point with any collidable object
        CollisionInfo collisionInfo = gameEnvironment.getClosestCollision(trajectory);

        if (collisionInfo == null) {
            // If no collision, move the ball to the end of the trajectory (without the radius adjusting)
            this.center = this.getVelocity().applyToPoint(this.center);

        } else {
            // Handle collision with the collidable object
            Point collisionPoint = collisionInfo.collisionPoint();
            Collidable collidable = collisionInfo.collisionObject();

            // Move the ball to "almost" the hit point, but just slightly before it
            double distX = collisionPoint.getX() - this.center.getX();
            double distY = collisionPoint.getY() - this.center.getY();
            double distance = Math.sqrt(distX * distX + distY * distY);
            double reducedDistance = distance - radius - 10; // Move just slightly before the collision point
            double ratio = reducedDistance / distance;

            this.center = new Point(this.center.getX() + ratio * distX, this.center.getY() + ratio * distY);

            // Notify the collidable object that a collision occurred and update velocity
            this.velocity = collidable.hit(this, collisionPoint, this.velocity);
        }
    }

    /**
     * Called when time passes, invoking the method responsible for moving the ball.
     */
    @Override
    public void timePassed() {
        moveOneStep(); // Call the method responsible for moving the ball
    }

    /**
     * Adds the ball to the game as a sprite.
     *
     * @param game = the game the ball is added to.
     */
    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    /**
     * Remove the ball from the game as a sprite.
     *
     * @param game = the game the ball is removed from.
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }
}
