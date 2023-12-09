package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task2 {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIRST_COPY = " - копия";
    private static final String N_COPY_OPEN = " - копия (";
    private static final String N_COPY_CLOSED = ")";
    private static final String PATH_IS_NULL = "Path is null";

    private Task2() {
    }

    public static Path cloneFile(Path path) {
        checkInput(path);
        Filename filename = Filename.of(path);

        Path copiedFile = createCopy(path, filename);
        LOGGER.info("Successful creation of the first copy of a file {}", path.getFileName().toString());

        return copiedFile;
    }

    public static Path cloneFile(String path) {
        if (path == null) {
            LOGGER.error(PATH_IS_NULL);
            throw new IllegalArgumentException(PATH_IS_NULL);
        }
        return cloneFile(Path.of(path));
    }

    private static Path createCopy(Path path, Filename filename) {
        String newFullFilename = filename.getAbsolutePath() + filename.getName() + FIRST_COPY + filename.getExtension();

        Path newPath = Path.of(newFullFilename);
        int copyNumber = 1;
        while (Files.exists(newPath)) {
            newFullFilename = filename.getAbsolutePath() + filename.getName() + N_COPY_OPEN + copyNumber + N_COPY_CLOSED
                + filename.getExtension();
            newPath = Path.of(newFullFilename);
            copyNumber++;
        }

        try {
            return Files.copy(path, newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("Something went wrong while copying the file");
            throw new RuntimeException(e);
        }
    }

    private static void checkInput(Path path) {
        if (path == null) {
            LOGGER.error(PATH_IS_NULL);
            throw new IllegalArgumentException(PATH_IS_NULL);
        }
        if (!Files.isRegularFile(path)) {
            LOGGER.error("The path {} is not to the file", path.toAbsolutePath().toString());
            throw new IllegalArgumentException("The path is not to the file");
        }

        LOGGER.info("Successful file verification");
    }
}
