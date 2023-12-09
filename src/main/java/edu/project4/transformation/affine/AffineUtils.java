package edu.project4.transformation.affine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AffineUtils {
    private static final double MIN = -1.0;
    private static final double MAX = 1.0;
    private static final int AFFINE_TRANSFORMATION_NUMBER = 7;
    private static final int RGB_MAX_VALUE = 256;

    private AffineUtils() {
    }

    public static List<Affine> getAffineTransformations() {
        Random random = new Random();
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        List<Affine> affine = new ArrayList<>();
        for (int i = 0; i < AFFINE_TRANSFORMATION_NUMBER; i++) {
            do {
                a = random.nextDouble(MIN, MAX);
                b = random.nextDouble(MIN, MAX);
                c = random.nextDouble(MIN, MAX);
                d = random.nextDouble(MIN, MAX);
                e = random.nextDouble(MIN, MAX);
                f = random.nextDouble(MIN, MAX);
            } while (checkBounds(a, b, d, e));
            int red = random.nextInt(RGB_MAX_VALUE);
            int green = random.nextInt(RGB_MAX_VALUE);
            int blue = random.nextInt(RGB_MAX_VALUE);
            affine.add(new Affine(a, b, c, d, e, f, red, green, blue));
        }

        return affine;
    }

    private static boolean checkBounds(double a, double b, double d, double e) {
        return (a * a + d * d < 1.0) && (b * b + e * e < 1.0)
            && (a * a + d * d + b * b + e * e < 1.0 + (a * e - b * d) * (a * e - b * d));
    }
}
