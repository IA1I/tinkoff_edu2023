package edu.project4.imageprocessor;

import edu.project4.utility.FractalImage;
import edu.project4.utility.Pixel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GammaCorrection implements ImageProcessor {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final double GAMMA = 2.2;

    @Override
    public void process(FractalImage image) {
        Pixel[][] pixels = image.data();
        double max = calculateNormal(pixels);

        changeColors(pixels, max);
    }

    private static void changeColors(Pixel[][] pixels, double max) {
        LOGGER.info("Start of color change");
        for (Pixel[] row : pixels) {
            for (Pixel pixel : row) {
                pixel.setNormal(pixel.getNormal() / max);
                double r = pixel.getR() * Math.pow(pixel.getNormal(), (1.0 / GAMMA));
                double g = pixel.getG() * Math.pow(pixel.getNormal(), (1.0 / GAMMA));
                double b = pixel.getB() * Math.pow(pixel.getNormal(), (1.0 / GAMMA));
                pixel.setColor((int) r, (int) g, (int) b);
            }
        }
        LOGGER.info("End of color change");
    }

    private double calculateNormal(Pixel[][] pixels) {
        LOGGER.info("Start calculating the norm");
        double max = 0.0;
        for (Pixel[] row : pixels) {
            for (Pixel pixel : row) {
                if (pixel.getHitCount() != 0) {
                    pixel.setNormal(Math.log10(pixel.getHitCount()));
                    max = Math.max(max, pixel.getNormal());
                }
            }
        }
        LOGGER.info("End calculating the norm");
        return max;
    }
}
