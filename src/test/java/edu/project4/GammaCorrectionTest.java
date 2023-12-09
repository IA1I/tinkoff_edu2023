package edu.project4;

import edu.project4.imageprocessor.GammaCorrection;
import edu.project4.imageprocessor.ImageProcessor;
import edu.project4.renderer.Renderer;
import edu.project4.renderer.SingleThreadedRenderer;
import edu.project4.transformation.Transformation;
import edu.project4.utility.FractalImage;
import edu.project4.utility.Pixel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class GammaCorrectionTest {

    @Test
    void should() {
        ImageProcessor processor = new GammaCorrection();
        int width = 200;
        int height = 200;
        FractalImage fractalImage = FractalImage.create(width, height);
        Renderer renderer = new SingleThreadedRenderer();
        FractalImage image =
            renderer.render(
                fractalImage,
                Transformation.getAllTransformations(),
                1000,
                (short) 20,
                1
            );
        Pixel[][] pixels = copyArray(image.data());
        processor.process(image);
        Pixel[][] processPixels = image.data();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                assertThat(processPixels[i][j].getR()).isLessThanOrEqualTo(pixels[i][j].getR());
                assertThat(processPixels[i][j].getG()).isLessThanOrEqualTo(pixels[i][j].getG());
                assertThat(processPixels[i][j].getB()).isLessThanOrEqualTo(pixels[i][j].getB());
            }
        }
    }

    Pixel[][] copyArray(Pixel[][] pixels) {
        Pixel[][] copied = new Pixel[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                copied[i][j] = new Pixel();
                copied[i][j].setColor(pixels[i][j].getR(), pixels[i][j].getG(), pixels[i][j].getB());
            }
        }

        return copied;
    }
}
