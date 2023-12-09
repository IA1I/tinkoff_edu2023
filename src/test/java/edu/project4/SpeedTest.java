package edu.project4;

import edu.project4.renderer.MultiThreadedRenderer;
import edu.project4.renderer.Renderer;
import edu.project4.renderer.SingleThreadedRenderer;
import edu.project4.transformation.Transformation;
import edu.project4.utility.FractalImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class SpeedTest {
    @Test
    void shouldCompareRenderSpeed(){
        int width = 1920;
        int height = 1080;
        FractalImage fractalImage = FractalImage.create(width, height);
        Renderer singleRenderer = new SingleThreadedRenderer();
        long startSingle = System.nanoTime();
        FractalImage image =
            singleRenderer.render(
                fractalImage,
                Transformation.getAllTransformations(),
                1000000,
                (short) 20,
                4
            );
        long endSingle = System.nanoTime()-startSingle;

        fractalImage = FractalImage.create(width, height);
        Renderer multiRenderer = new MultiThreadedRenderer();
        long startMulti = System.nanoTime();
        image =
            multiRenderer.render(
                fractalImage,
                Transformation.getAllTransformations(),
                1000000,
                (short) 20,
                4
            );
        long endMulti = System.nanoTime()-startMulti;
        Logger logger = LogManager.getLogger();
        logger.info("Single {}", endSingle/1_000_000_000.0);
        logger.info("Multi {}", endMulti/1_000_000_000.0);
    }
}
