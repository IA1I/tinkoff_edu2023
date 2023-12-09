package edu.project4.transformation;

import edu.project4.utility.Point;

public class Ex implements Transformation {

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan(x / y);
        double p0 = Math.sin(theta + r);
        double p1 = Math.cos(theta - r);
        return new Point(r * (p0 * p0 * p0 + p1 * p1 * p1), r * (p0 * p0 * p0 - p1 * p1 * p1));
    }
}
