package edu.project4.renderer;

import edu.project4.transformation.Transformation;
import edu.project4.utility.FractalImage;
import java.util.List;

public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        int symmetry
    );
}
