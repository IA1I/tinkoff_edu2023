package edu.project4;

import edu.project4.imageutils.ImageUtils;
import edu.project4.utility.FractalImage;
import edu.project4.utility.ImageFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ImageUtilsTest {
    @Test
    void shouldThrowIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ImageUtils.save(null, Path.of(""), ImageFormat.BMP)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> ImageUtils.save(FractalImage.create(100, 100), null, ImageFormat.BMP)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> ImageUtils.save(FractalImage.create(100, 100), Path.of(""), null)
        );
    }

    @Test
    void shouldCreateFileIfItNotExist() {
        Path path = Path.of("test.png");
        ImageUtils.save(FractalImage.create(100, 100), path, ImageFormat.PNG);
        assertThat(Files.exists(path)).isTrue();
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldSavePNGFile() {
        Path path = Path.of("test.png");
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageUtils.save(FractalImage.create(100, 100), path, ImageFormat.PNG);
        assertThat(Files.exists(path)).isTrue();
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldSaveJPEGFile() {
        Path path = Path.of("test.jpeg");
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageUtils.save(FractalImage.create(100, 100), path, ImageFormat.JPEG);
        assertThat(Files.exists(path)).isTrue();
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldSaveBMPFile() {
        Path path = Path.of("test.bmp");
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageUtils.save(FractalImage.create(100, 100), path, ImageFormat.BMP);
        assertThat(Files.exists(path)).isTrue();
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
