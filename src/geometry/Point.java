package geometry;

/**
 * This class create a 2D point.
 */
public class Point {
    // The x-coordinate of the point
    private final double x;

    // The y-coordinate of the point
    private final double y;

    // Threshold for double comparison
    private static final double THRESHOLD = 0.0001;

    /**
     * Constructs a point at the specified (x, y) location.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the distance between this point and another point. by using the distance formula
     *
     * @param other  = the other point to which the distance is calculated
     * @return the distance between this point and the other point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Checks if this point is equal to another point. Two points are considered equal if
     * their x and y coordinates are both within a small threshold of each other
     *
     * @param other = the other point to compare with this point
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return Math.abs(this.x - other.x) < THRESHOLD && Math.abs(this.y - other.y) < THRESHOLD;
    }

    /**
     * @return the x-coordinate of this point
     */
    public double getX() {
        return x;
    }

    /**
     *@return the y-coordinate of this point
     */
    public double getY() {
        return y;
    }
}
