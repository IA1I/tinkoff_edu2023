package edu.project4.transformation;

import edu.project4.utility.Point;

public class Julia implements Transformation {

    private static final double OMEGA = 2.5;

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.sqrt(x * x + y * y);
        r = Math.sqrt(r);
        double theta = Math.atan(x / y);
        return new Point(r * Math.cos(theta / 2 + OMEGA), r * Math.sin(theta / 2 + OMEGA));
    }
}
