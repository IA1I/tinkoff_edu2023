package edu.project4.transformation;

import edu.project4.utility.Point;

public class Exponential implements Transformation {

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double exp = Math.exp(x - 1);
        return new Point(exp * Math.cos(Math.PI * y), exp * Math.sin(Math.PI * y));
    }
}
