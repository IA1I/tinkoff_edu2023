package edu.project4.transformation;

import edu.project4.utility.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    static List<Transformation> getAllTransformations() {
        List<Transformation> transformations = new ArrayList<>();
        transformations.add(new Linear());
        transformations.add(new Sinusoidal());
        transformations.add(new Spherical());
        transformations.add(new Swirl());
        transformations.add(new Polar());
        transformations.add(new Handkerchief());
        transformations.add(new Heart());
        transformations.add(new Spiral());
        transformations.add(new Ex());
        transformations.add(new Julia());
        transformations.add(new Fisheye());
        transformations.add(new Exponential());
        transformations.add(new Eyefish());

        return transformations;
    }

    static List<Transformation> getRandomTransformation(long seed) {
        List<Transformation> all = getAllTransformations();
        List<Transformation> randomTransformation = new ArrayList<>();
        Random random = new Random(seed);
        for (int i = 0; i < random.nextInt(1, all.size()); i++) {
            randomTransformation.add(all.get(random.nextInt(all.size())));
        }

        return randomTransformation;
    }
}
