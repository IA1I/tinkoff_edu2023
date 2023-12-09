package edu.project4.transformation.affine;

import edu.project4.transformation.Transformation;
import edu.project4.utility.Point;
import java.awt.Color;

public class Affine implements Transformation {
    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
    private Color color;

    @SuppressWarnings("ParameterNumber")
    public Affine(double a, double b, double c, double d, double e, double f, int red, int green, int blue) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.color = new Color(red, green, blue);
    }

    @Override
    public Point apply(Point point) {

        double x = point.getX();
        double y = point.getY();
        return new Point(a * x + b * y + c, d * x + e * y + f);
    }

    public Color getColor() {
        return color;
    }
}
