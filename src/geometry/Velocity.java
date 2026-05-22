package geometry;

/**
 * This class represents a 2D velocity with components dx and dy.
 */
public class Velocity {
    private final double dx; // The change in x-coordinate per unit of time
    private final double dy; // The change in y-coordinate per unit of time

    /**
     * Constructs a velocity with the given change in x-coordinate (dx) and y-coordinate (dy).
     *
     * @param dx = The change in x-coordinate per unit of time.
     * @param dy = The change in y-coordinate per unit of time.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creates a velocity from the given angle (dx) and speed (dy).
     *
     * @param angle = The angle in degrees.
     * @param speed = The speed of the velocity.
     * @return A new geometry.Velocity calculated from the given angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Convert angle from degrees to radians
        double radians = Math.toRadians(90 - angle); // Adjusting the angle to fit the Cartesian coordinates
        // So "0" will be "Up"

        // Calculate change in x-coordinate and y-coordinate
        double dx = speed * Math.cos(radians);
        double dy = -speed * Math.sin(radians); // Negative sign for y-coordinate to match the Cartesian coordinates

        return new Velocity(dx, dy); // Return a new geometry.Velocity instance
    }


    /**
     * Applies this velocity to a given point, returning a new point with updated coordinates.
     *
     * @param p = The point to which the velocity is applied.
     * @return A new geometry.Point instance representing the result of applying this velocity to the given point.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy); // Update point coordinates with velocity components
    }

    /**
     * @return The dx.
     */
    public double getDx() {
        return dx;
    }

    /**
     * @return The dy.
     */
    public double getDy() {
        return dy;
    }
}