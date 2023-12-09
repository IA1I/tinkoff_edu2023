package edu.project4.transformation;

import edu.project4.utility.Point;

public class Fisheye implements Transformation {

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.sqrt(x * x + y * y);
        return new Point(y * (2 / (r + 1)), x * (2 / (r + 1)));
    }
}
