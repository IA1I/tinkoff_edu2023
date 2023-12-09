package edu.project4.imageutils;

import edu.project4.utility.FractalImage;
import edu.project4.utility.ImageFormat;
import edu.project4.utility.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ImageUtils {
    private static final Logger LOGGER = LogManager.getLogger();

    private ImageUtils() {
    }

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        if (image == null || filename == null || format == null) {
            LOGGER.info("Input is null");
            throw new IllegalArgumentException();
        }
        BufferedImage bufferedImage = createBufferedImage(image);

        try {
            if (!Files.exists(filename)) {
                Files.createFile(filename);
                LOGGER.info("Created new file {}", filename.toString());
            }
            ImageIO.write(bufferedImage, format.toString(), filename.toFile());
            LOGGER.info("Image saved");
        } catch (IOException e) {
            LOGGER.error("Problems with saving image");
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage createBufferedImage(FractalImage image) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel pixel = image.pixel(x, y);
                int r = pixel.getR();
                int g = pixel.getG();
                int b = pixel.getB();
                bufferedImage.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return bufferedImage;
    }
}
