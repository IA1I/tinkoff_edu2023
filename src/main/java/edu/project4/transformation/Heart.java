package edu.project4.transformation;

import edu.project4.utility.Point;

public class Heart implements Transformation {

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan(x / y);
        return new Point(r * Math.sin(theta * r), -r * Math.cos(theta * r));
    }
}
