package edu.project4;

import edu.project4.imageprocessor.GammaCorrection;
import edu.project4.imageprocessor.ImageProcessor;
import edu.project4.renderer.Renderer;
import edu.project4.renderer.SingleThreadedRenderer;
import edu.project4.transformation.Transformation;
import edu.project4.utility.FractalImage;
import edu.project4.utility.Pixel;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SingleThreadedRendererTest {
    @Test
    void shouldRenderImage() {
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
        Pixel[][] pixels = image.data();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                assertThat(pixels[i][j].getHitCount()).isGreaterThanOrEqualTo(0);
                assertThat(pixels[i][j].getR()).isBetween(0, 255);
                assertThat(pixels[i][j].getG()).isBetween(0, 255);
                assertThat(pixels[i][j].getB()).isBetween(0, 255);
            }
        }
    }
}
