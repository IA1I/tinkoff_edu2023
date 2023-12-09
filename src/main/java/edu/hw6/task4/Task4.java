package edu.hw6.task4;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task4 {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SPECIFIED_STRING_TO_WRITE =
        "Programming is learned by writing programs. ― Brian Kernighan";

    private Task4() {
    }

    public static void writeToFile(Path path) {
        Path file = checkPath(path);
        try (
            OutputStream outputStream = Files.newOutputStream(file);
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new CRC32());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                bufferedOutputStream,
                StandardCharsets.UTF_8
            );
            PrintWriter printWriter = new PrintWriter(outputStreamWriter)
        ) {
            printWriter.write(SPECIFIED_STRING_TO_WRITE);
            LOGGER.info("Successful write to file");
        } catch (IOException e) {
            LOGGER.error("An error {} occurred while writing to the file", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Path checkPath(Path path) {
        if (path == null) {
            LOGGER.error("Path is null");
            throw new IllegalArgumentException();
        }
        if (Files.isDirectory(path)) {
            LOGGER.error("Path to directory");
            throw new IllegalArgumentException();
        }
        if (Files.exists(path)) {
            return path;
        }

        try {
            return Files.createFile(path);
        } catch (IOException e) {
            LOGGER.error("An error {} occurred while creating the file", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
