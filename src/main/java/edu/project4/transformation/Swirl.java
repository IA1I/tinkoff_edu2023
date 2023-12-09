package edu.project4.transformation;

import edu.project4.utility.Point;

public class Swirl implements Transformation {

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = x * x + y * y;
        return new Point(x * Math.sin(r) - y * Math.cos(r), x * Math.sin(r) + y * Math.cos(r));
    }
}
