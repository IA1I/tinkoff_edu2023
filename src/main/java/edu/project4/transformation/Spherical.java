package edu.project4.transformation;

import edu.project4.utility.Point;

public class Spherical implements Transformation {

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = x * x + y * y;
        return new Point(x / r, y / r);
    }
}
