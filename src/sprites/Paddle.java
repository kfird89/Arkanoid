package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.List;

import gameSettings.Game;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * This class Create a 2d sprites.Paddle in the game.
 * The paddle can move left and right based on user input and can collide with balls.
 */
public class Paddle implements Collidable, Sprite {
    private final KeyboardSensor keyboard;
    private final Rectangle rectangle;
    private final Color color;
    private final double speed;
    private final List<Ball> balls; // Field to hold the list of balls

    /**
     * Create a new sprites.Paddle.
     *
     * @param keyboard = the keyboard sensor to detect user input
     * @param rectangle = the rectangle representing the paddle's shape
     * @param color = the color of the paddle
     * @param speed = the speed at which the paddle moves
     * @param balls = the list of balls in the game
     */
    public Paddle(KeyboardSensor keyboard, Rectangle rectangle, Color color, double speed, List<Ball> balls) {
        this.keyboard = keyboard;
        this.rectangle = rectangle;
        this.color = color;
        this.speed = speed;
        this.balls = balls; // Initialize the list of balls
    }

    /**
     * Handles paddle movement based on keyboard input during each game tick.
     * Moves the paddle left if the left arrow key is pressed and there is no ball conflict on the left side.
     * Moves the paddle right if the right arrow key is pressed and there is no conflict on the right side.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY) && !isConflictOnLeft()) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY) && !isConflictOnRight()) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given DrawSurface.
     *
     * @param d the DrawSurface to draw the paddle on
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Draw the paddle
        d.setColor(color);
        d.fillRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    /**
     * @return the collision rectangle of the paddle
     */
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    /**
     * Notifies the paddle that it has been collided with at the specified point with the given velocity.
     * The paddle is divided into 5 regions, each corresponding to a different bounce angle
     * And calculates the new velocity of a ball.
     *
     * @param collisionPoint = the point on the paddle where the collision occurred
     * @param currentVelocity = the current velocity of the ball before the collision
     * @return the new velocity of the ball after the collision with the paddle
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double paddleWidth = rectangle.getWidth();

        // Calculate the region based on the collision point
        double regionWidth = paddleWidth / 5; // Divide the paddle into 5 equal regions
        int region = (int) ((collisionPoint.getX() - rectangle.getUpperLeft().getX()) / regionWidth) + 1;

        // Define angles for each region
        double angle;
        switch (region) {
            case 1:
                angle = 300; // 300 degrees (-60 degrees)
                break;
            case 2:
                angle = 330; // 330 degrees (-30 degrees)
                break;
            case 3:
                // Keep the current horizontal direction for the middle region
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            case 4:
                angle = 30; // 30 degrees
                break;
            case 5:
                angle = 60; // 60 degrees
                break;
            default:
                // In case of an unexpected region, reflect the ball vertically
                angle = Math.toDegrees(Math.atan2(currentVelocity.getDy(), currentVelocity.getDx()));
        }

        // Preserve the speed magnitude
        double speed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                + currentVelocity.getDy() * currentVelocity.getDy());

        // Return the adjusted velocity using the angle and speed
        return Velocity.fromAngleAndSpeed(angle, speed);
    }

    /**
     * Moves the paddle to the left.
     */
    public void moveLeft() {
        double newX = rectangle.getUpperLeft().getX() - speed;
        if (newX < 20) { // If paddle moves off the left edge
            newX = Game.WIDTH - rectangle.getWidth() - 20;
        }
        // Wrap around to the right edge
        rectangle.setUpperLeft(new Point(newX, rectangle.getUpperLeft().getY()));
    }

    /**
     * Moves the paddle to the right.
     */
    public void moveRight() {
        double newX = rectangle.getUpperLeft().getX() + speed;
        if ((newX + rectangle.getWidth() > Game.WIDTH - 20)) { // If paddle moves off the right edge
            newX = 20;
        }
        // Wrap around to the left edge
        rectangle.setUpperLeft(new Point(newX, rectangle.getUpperLeft().getY()));
    }

    /**
     * Handles the collision with a ball.
     * Adjusts the ball's position and velocity based on the collision point.
     *
     * @param ball = the ball that collided with the paddle
     */
    private void handleCollision(Ball ball) {
        double speed = Math.sqrt(ball.getVelocity().getDx() * ball.getVelocity().getDx()
                + ball.getVelocity().getDy() * ball.getVelocity().getDy());

        // Adjust the ball's center X location to prevent it from getting inside the paddle
        if (isBallInLeft(ball)) {
            ball.setX(rectangle.getUpperLeft().getX() - ball.getSize() - 1);
        } else if (isBallInRight(ball)) {
            ball.setX(rectangle.getUpperLeft().getX() + rectangle.getWidth() + ball.getSize() + 1);
        }

        // Get the paddle's center
        double paddleCenterX = rectangle.getUpperLeft().getX() + (rectangle.getWidth() / 2);

        // Calculate the distance of the ball's hit point from the center of the paddle
        double distanceFromCenter = ball.getCenter().getX() - paddleCenterX;

        // Calculate the bounce angle based on the normalized distance
        double bounceAngle;
        if (distanceFromCenter < 0) {
            // sprites.Ball hit the left side of the paddle
            bounceAngle = 270; // Angle for a bounce towards the left (Custom angle)
        } else {
            // sprites.Ball hit the right side of the paddle
            bounceAngle = 85; // Angle for a bounce towards the right (Custom angle)
        }

        // Update the ball's velocity based on the bounce angle
        ball.setVelocity(Velocity.fromAngleAndSpeed(bounceAngle, speed));
    }

    /**
     * Checks if any ball will collide with the right side of the paddle.
     *
     * @return true if a ball will hit the paddle on the right side, false otherwise
     */
    public boolean isConflictOnRight() {
        for (Ball ball : balls) {
            if (isBallInRight(ball) && ballWillHitPaddle(ball)) {
                handleCollision(ball); // Handle the collision if it occurs
                return true; // Return true since a ball will hit the paddle on the right side
            }
        }
        return false; // Return false if no ball will hit the paddle on the right side
    }

    /**
     * Checks if  any ball will collide with the left side of the paddle.
     *
     * @return true if a ball will hit the paddle on the left side, false otherwise
     */
    public boolean isConflictOnLeft() {
        for (Ball ball : balls) {
            if (isBallInLeft(ball) && ballWillHitPaddle(ball)) {
                handleCollision(ball); // Handle the collision if it occurs
                return true; // Return true since a ball will hit the paddle on the right side
            }
        }
        return false; // Return false if no ball will hit the paddle on the right side
    }

    /**
     * Checks if a ball is on the left side of the paddle.
     *
     * @param ball = the ball to check
     * @return true if the ball is on the left side of the paddle, false otherwise
     */
    private boolean isBallInLeft(Ball ball) {
        return ball.getX() + ball.getSize() + 1 >= rectangle.getUpperLeft().getX()
                && ball.getX() + ball.getSize() + 1 <= rectangle.getUpperLeft().getX() + rectangle.getWidth();
    }

    /**
     * Checks if a ball is on the right side of the paddle.
     *
     * @param ball = the ball to check
     * @return true if the ball is on the right side of the paddle, false otherwise
     */
    private boolean isBallInRight(Ball ball) {
        return ball.getX() - ball.getSize() - 1 <= rectangle.getUpperLeft().getX() + rectangle.getWidth()
                && ball.getX() - ball.getSize() - 1 >= rectangle.getUpperLeft().getX();
    }

    /**
     * Checks if a ball will hit the paddle.
     *
     * @param ball = the ball to check
     * @return true if the ball will hit the paddle, false otherwise
     */
    private boolean ballWillHitPaddle(Ball ball) {
        return ball.getY() + ball.getSize() >= rectangle.getUpperLeft().getY()
                && ball.getY() + ball.getSize() <= rectangle.getUpperLeft().getY() + rectangle.getHeight();
    }

    /**
     * Adds the paddle to the game as both a collidable and a sprite.
     *
     * @param game = the game to add the paddle to.
     */
    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }
}
