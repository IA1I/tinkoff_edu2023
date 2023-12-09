package edu.project4.renderer;

import edu.project4.transformation.Transformation;
import edu.project4.transformation.affine.Affine;
import edu.project4.utility.Point;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RendererUtils {
    public static final double X_MIN = -1.777;
    public static final double X_MAX = 1.777;
    public static final double Y_MIN = -1.0;
    public static final double Y_MAX = 1.0;

    private RendererUtils() {
    }

    public static Point getRotatedPoint(Point point, double theta) {
        double newX = point.getX() * Math.cos(theta) - point.getY() * Math.sin(theta);
        double newY = point.getX() * Math.sin(theta) + point.getY() * Math.cos(theta);
        return new Point(newX, newY);
    }

    public static boolean isPointInRightArea(Point point) {
        return ((point.getX() >= X_MIN) && (point.getX() <= X_MAX)
            && (point.getY() >= Y_MIN) && (point.getY() <= Y_MAX));
    }

    public static Point getRandomPoint() {
        double x = ThreadLocalRandom.current().nextDouble(X_MIN, X_MAX);
        double y = ThreadLocalRandom.current().nextDouble(Y_MIN, Y_MAX);

        return new Point(x, y);
    }

    public static Transformation getRandomTransformation(List<Transformation> transformations) {
        return transformations.get(ThreadLocalRandom.current().nextInt(transformations.size()));
    }

    public static Affine getRandomAffine(List<Affine> transformations) {
        return transformations.get(ThreadLocalRandom.current().nextInt(transformations.size()));
    }
}
