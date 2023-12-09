package edu.project4.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record FractalImage(Pixel[][] data, int width, int height) {
    private static final Logger LOGGER = LogManager.getLogger();

    public static FractalImage create(int width, int height) {
        if (width < 0 || height < 0) {
            LOGGER.error("Wrong image size");
            throw new IllegalArgumentException();
        }
        Pixel[][] data = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = new Pixel();
            }
        }
        LOGGER.info("Created fractal image {}x{}", width, height);
        return new FractalImage(data, width, height);
    }

    public Pixel pixel(int x, int y) {
        return data[x][y];
    }
}
