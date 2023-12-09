package edu.project4.transformation;

import edu.project4.utility.Point;

public class Sinusoidal implements Transformation {
    @Override
    public Point apply(Point point) {
        return new Point(Math.sin(point.getX()), Math.sin(point.getY()));
    }
}
