package edu.project4.renderer;

import edu.project4.transformation.Transformation;
import edu.project4.transformation.affine.Affine;
import edu.project4.transformation.affine.AffineUtils;
import edu.project4.utility.FractalImage;
import edu.project4.utility.Pixel;
import edu.project4.utility.Point;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.project4.renderer.RendererUtils.X_MAX;
import static edu.project4.renderer.RendererUtils.X_MIN;
import static edu.project4.renderer.RendererUtils.Y_MAX;
import static edu.project4.renderer.RendererUtils.Y_MIN;
import static edu.project4.renderer.RendererUtils.getRandomAffine;
import static edu.project4.renderer.RendererUtils.getRandomPoint;
import static edu.project4.renderer.RendererUtils.getRandomTransformation;
import static edu.project4.renderer.RendererUtils.getRotatedPoint;
import static edu.project4.renderer.RendererUtils.isPointInRightArea;

public class MultiThreadedRenderer implements Renderer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int THREADS_NUMBER = 4;
    private static final int START_STEP = -20;
    private List<Affine> affineTransformation;

    @Override
    public FractalImage render(
        FractalImage canvas,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        int symmetry
    ) {
        LOGGER.info("Start render");
        affineTransformation = AffineUtils.getAffineTransformations();
        int width = canvas.width();
        int height = canvas.height();
        Thread[] threads = getThreads(
            canvas.data(),
            width,
            height,
            variations,
            samples / THREADS_NUMBER,
            iterPerSample,
            symmetry
        );
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        LOGGER.info("End render");
        return canvas;
    }

    private Thread[] getThreads(
        Pixel[][] pixels,
        int width,
        int height,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        int symmetry
    ) {
        Thread[] threads = new Thread[THREADS_NUMBER];
        for (int i = 0; i < THREADS_NUMBER; i++) {
            threads[i] =
                new Thread(() -> render(pixels, width, height, variations, samples, iterPerSample, symmetry));
        }

        return threads;
    }

    private void render(
        Pixel[][] pixels,
        int width,
        int height,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        int symmetry
    ) {
        for (int num = 0; num < samples; ++num) {
            Point point = getRandomPoint();
            for (short step = START_STEP; step < iterPerSample; ++step) {
                Affine affine = getRandomAffine(affineTransformation);
                Transformation variation = getRandomTransformation(variations);
                point = affine.apply(point);
                point = variation.apply(point);
                if (step >= 0) {
                    double theta = 0.0;
                    for (int i = 0; i < symmetry; i++) {
                        theta += ((2 * Math.PI) / symmetry);
                        Point rotated = getRotatedPoint(point, theta);
                        if (isPointInRightArea(rotated)) {
                            int x = (int) (width - (((X_MAX - rotated.getX()) / (X_MAX - X_MIN)) * width));
                            int y = (int) (height - (((Y_MAX - rotated.getY()) / (Y_MAX - Y_MIN)) * height));
                            int r = affine.getColor().getRed();
                            int g = affine.getColor().getGreen();
                            int b = affine.getColor().getBlue();
                            synchronized (pixels[x][y]) {
                                if (pixels[x][y].getHitCount() != 0) {
                                    Pixel pixel = pixels[x][y];
                                    r = (r + pixel.getR()) / 2;
                                    g = (g + pixel.getG()) / 2;
                                    b = (b + pixel.getB()) / 2;
                                }
                                pixels[x][y].setColor(r, g, b);
                                pixels[x][y].increaseHit();
                            }
                        }
                    }
                }
            }
        }
    }
}
