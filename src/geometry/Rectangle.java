package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * This class create a 2d rectangle from its upper-left corner point, width, and height.
 */
public class Rectangle {
    private Point upperLeft;
    private final double width;
    private final double height;

    /**
     * Create a new rectangle with specified location and width/height.
     *
     * @param upperLeft = the upper-left corner of the rectangle
     * @param width = the width of the rectangle
     * @param height = the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line = the line to check for intersections with the rectangle
     * @return a list of intersection points between the line and the rectangle's edges
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();

        // Define the four corners of the rectangle
        Point upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        Point lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        Point lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);

        // Define the edges of the rectangle as lines
        Line[] edges = {
                new Line(upperLeft, upperRight), // Top
                new Line(lowerRight, lowerLeft), // Bottom
                new Line(upperLeft, lowerLeft), // Left
                new Line(lowerRight, upperRight) // RIGHT
        };

        // Check each edge for intersection with the specified line
        for (Line edge : edges) {
            Point intersection = line.intersectionWith(edge);
            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }

        return intersectionPoints;
    }

    /**
     * @return the upper-left corner point
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the height of the rectangle
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param upperLeft the new upper-left corner point
     */
    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft;
    }
}
