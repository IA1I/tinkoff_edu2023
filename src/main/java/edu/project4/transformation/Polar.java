package edu.project4.transformation;

import edu.project4.utility.Point;

public class Polar implements Transformation {

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = x * x + y * y;
        double theta = Math.atan(x / y);
        return new Point(theta / Math.PI, r - 1);
    }
}
