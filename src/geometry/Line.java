package geometry;

import java.util.List;

/**
 * This class create a 2D geometry.Line from 2 points.
 */

public class Line {
    private final Point start; // The start point of the line
    private final Point end;   // The end point of the line

    /**
     * Constructs a line segment with the specified start and end points.
     *
     * @param start = the start point of the line
     * @param end   = the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a line segment with the specified coordinates
     * The start and end points are determined by their order to ensure consistency.
     *
     * @param x1 = the x-coordinate of the first point
     * @param y1 = the y-coordinate of the first point
     * @param x2 = the x-coordinate of the second point
     * @param y2 = the y-coordinate of the second point
     */
    public Line(double x1, double y1, double x2, double y2) {
        if (x1 < x2 || (x1 == x2 && y1 < y2)) {
            this.start = new Point(x1, y1);
            this.end = new Point(x2, y2);
        } else {
            this.start = new Point(x2, y2);
            this.end = new Point(x1, y1);
        }
    }

    /**
     * @return the length of the line segment
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Find the middle point by using math formula.
     *
     * @return the middle point of the line segment
     */
    public Point middle() {
        double middleX = (end.getX() + start.getX()) / 2;
        double middleY = (end.getY() + start.getY()) / 2;
        return new Point(middleX, middleY);
    }

    /**
     * @return the start point of the line segment
     */
    public Point start() {
        return start;
    }

    /**
     * @return the end point of the line segment
     */
    public Point end() {
        return end;
    }

    /**
     * Checks if this line segment intersects with another line segment.
     *
     * @param other = the other line segment to check for intersection
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        // Check if the lines share any common start/end points
        if (this.start.equals(other.start) || this.start.equals(other.end)
                || this.end.equals(other.start) || this.end.equals(other.end)) {
            return true;
        }

        // Get the line equation from the geometry.LineEquation class
        LineEquation lineEquation1 = new LineEquation(this);
        LineEquation lineEquation2 = new LineEquation(other);

        // Get the line equation slope (m) and the y-intercept (b)
        // from the geometry.LineEquation class of the first line
        double m1 = lineEquation1.getM();
        double b1 = lineEquation1.getB();

        // Get the line equation slope (m) and the y-intercept (b)
        // from the geometry.LineEquation class of the other line
        double m2 = lineEquation2.getM();
        double b2 = lineEquation2.getB();

        // Check if lines are vertical
        boolean isVertical1 = Double.isInfinite(m1);
        boolean isVertical2 = Double.isInfinite(m2);

        if (isVertical1 && isVertical2) {
            // The 2 lines are vertical, and if they have the same point, they interact
            return this.start.equals(other.start);

        } else if (isVertical1) {
            // If line 1 is vertical
            // Calculate the y-coordinate of intersection using x-coordinate of vertical line
            double x = this.start.getX();
            double y = m2 * x + b2;
            // Check if the calculated intersection point lies within the y-coordinate bounds of the first line
            // and within the x-coordinate bounds of the second line
            return isBetween(y, this.start.getY(), this.end.getY())
                    && isBetween(x, other.start.getX(), other.end.getX());

        } else if (isVertical2) {
            // If line 2 is vertical
            // Calculate the y-coordinate of intersection using x-coordinate of vertical line
            double x = other.start.getX();
            double y = m1 * x + b1;
            // Check if the calculated intersection point lies within the y-coordinate bounds of the first line
            // and within the x-coordinate bounds of the second line
            return isBetween(y, other.start.getY(), other.end.getY())
                    && isBetween(x, this.start.getX(), this.end.getX());

        } else {
            // Check if lines are parallel or coincident
            if (m1 == m2) {
                // For horizontal lines, check if they have the same y-intercept (coincident lines)
                if (m1 == 0) {
                    return b1 == b2;
                }
                // For non-horizontal lines, return false since parallel lines never intersect
                return false;
            }

            // Calculate the x-coordinate of the intersection point
            double x = (b2 - b1) / (m1 - m2);

            // Check if the x-coordinate lies within the bounds of both lines
            double minX1 = Math.min(this.start().getX(), this.end().getX());
            double maxX1 = Math.max(this.start().getX(), this.end().getX());
            double minX2 = Math.min(other.start().getX(), other.end().getX());
            double maxX2 = Math.max(other.start().getX(), other.end().getX());

            // If the x-coordinate of the intersection point lies within the x-coordinate bounds of both lines
            // return True, else return False
            return x >= Math.max(minX1, minX2) && x <= Math.min(maxX1, maxX2);
        }
    }

    /**
     * Checks if the specified value is between the start and end values.
     *
     * @param value = the value to check
     * @param start = the start value
     * @param end   = the end value
     * @return true if the value is between the start and end values, false otherwise
     */
    private boolean isBetween(double value, double start, double end) {
        return value >= Math.min(start, end) && value <= Math.max(start, end);
    }

    /**
     * Checks if this line segment intersects with 2 other lines segment.
     *
     * @param other1 = the first other line segment to check for intersection
     * @param other2 = the second other line segment to check for intersection
     * @return true if this 2 lines intersect with this line, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Calculates the intersection point of this line segment with another line segment.
     *
     * @param other = the other line segment to check for intersection
     * @return the intersection point if the lines intersect, null otherwise
     */
    public Point intersectionWith(Line other) {
        // First check if the lines are intersect
        if (!this.isIntersecting(other)) {
            return null;
        }

        // Get the line equation from the geometry.LineEquation class
        LineEquation lineEquation1 = new LineEquation(this);
        LineEquation lineEquation2 = new LineEquation(other);

        // Get the line equation slope (m) and the y-intercept (b)
        // from the geometry.LineEquation class of the first line
        double m1 = lineEquation1.getM();
        double b1 = lineEquation1.getB();

        // Get the line equation slope (m) and the y-intercept (b)
        // from the geometry.LineEquation class of the other line
        double m2 = lineEquation2.getM();
        double b2 = lineEquation2.getB();

        double x, y;

        //Get from geometry.LineEquation class true or false if the line is vertical
        boolean isVertical1 = lineEquation1.isVertical();
        boolean isVertical2 = lineEquation2.isVertical();

        if (isVertical1 && isVertical2) {
            // Both lines are vertical and parallel, they do not intersect / have infinite intersection points
            return null;
        } else if (isVertical1) {
            // geometry.Line 1 is vertical, and get the intersection point
            x = lineEquation1.getB();
            y = m2 * x + b2;
        } else if (isVertical2) {
            // geometry.Line 2 is vertical, and get the intersection point
            x = lineEquation2.getB();
            y = m1 * x + b1;
        } else if (m1 == 0 && m2 == 0) {
            // Both lines are horizontal and parallel, they do not intersect
            return null;
        } else if (m1 == 0) {
            // geometry.Line 1 is horizontal
            y = b1;
            x = (y - b2) / m2;
        } else if (m2 == 0) {
            // geometry.Line 2 is horizontal
            y = b2;
            x = (y - b1) / m1;
        } else {
            // Both lines are not vertical and not horizontal, so use a math formula to find the intersection point
            x = (b2 - b1) / (m1 - m2);
            y = m1 * x + b1;
        }

        return new Point(x, y);
    }

    /**
     * Checks if this line segment is equal to another line segment.
     * Two lines are considered equal if they have the same start and end points,
     * regardless of the order of the points.
     *
     * @param other =  the other line segment to compare with this line
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start()) && this.end.equals(other.end()))
                || (this.start.equals(other.end()) && this.end.equals(other.start()));
    }
/**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     *
     * @param  rect = the rectangle to check if intersect with the line.
     * @return the closest intersection point
 */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Get all intersection points with the rectangle
        List<Point> intersections = rect.intersectionPoints(this);

        // If there are no intersections, return null
        if (intersections.isEmpty()) {
            return null;
        }

        // Initialize the closest point and the minimum distance
        Point closestIntersection  = intersections.get(0);
        double closestDistance = this.start.distance(closestIntersection);

        // Iterate over all intersection points to find the closest one
        for (Point point : intersections) {
            double distance = this.start.distance(point);
            if (distance < closestDistance) {
                closestIntersection  = point;
                closestDistance  = distance;
            }
        }
        return closestIntersection;
    }
}
