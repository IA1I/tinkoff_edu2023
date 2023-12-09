package edu.project4.transformation;

import edu.project4.utility.Point;

public class Linear implements Transformation {
    @Override
    public Point apply(Point point) {
        return point;
    }
}
