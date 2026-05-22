package geometry;

/**
 * The class create a line equation for a line in a form of (y = mx + b).
 */
public class LineEquation {
    private final double m; // slope
    private final double b; // y-intercept
    private final boolean vertical;

    /**
     * Constructs a geometry.LineEquation object from a given geometry.Line.
     *
     * @param line The geometry.Line from the geometry.Line class, to construct to him his geometry.LineEquation.
     */
    public LineEquation(Line line) {
        double x1 = line.start().getX();
        double y1 = line.start().getY();
        double x2 = line.end().getX();
        double y2 = line.end().getY();

        // Determine if the line is vertical
        if (x1 == x2) {
            this.vertical = true;
            this.m = Double.POSITIVE_INFINITY; // Vertical line
            this.b = x1;
        } else {
            this.vertical = false;
            // Calculate slope (m) and y-intercept (b) for not vertical line
            this.m = (y2 - y1) / (x2 - x1);
            this.b = y1 - m * x1;
        }
    }

    /**
     * @return The slope (m) of the line.
     */
    public double getM() {
        return m;
    }

    /**
     * @return The y-intercept (b) of the line.
     */
    public double getB() {
        return b;
    }

    /**
     * @return True if the line is vertical, false otherwise
     */
    public boolean isVertical() {
        return vertical;
    }
}
