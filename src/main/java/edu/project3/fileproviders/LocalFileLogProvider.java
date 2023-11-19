package edu.project3.fileproviders;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocalFileLogProvider implements LogProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Path> provide(String path) {
        Path filePath = getFilepath(path);
        if (filePath == null || !Files.exists(filePath)) {
            LOGGER.info("Return empty list of files");
            return List.of();
        }
        if (Files.isRegularFile(filePath)) {
            LOGGER.info("Return list of one file");
            return List.of(filePath);
        }
        if (Files.isDirectory(filePath)) {
            List<Path> filePaths = new ArrayList<>();
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(filePath)) {
                for (Path entry : entries) {
                    if (Files.isRegularFile(entry)) {
                        filePaths.add(entry);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Something went wrong when retrieving directories: {}", e.getMessage());
            }
            LOGGER.info("Return list of files");
            return filePaths;
        }

        return List.of();
    }

    private Path getFilepath(String path) {
        try {
            return Path.of(path);
        } catch (NullPointerException e) {
            LOGGER.info("An empty path was passed: {}", e.getMessage());
            return null;
        } catch (InvalidPathException e) {
            LOGGER.error("Error creating file: {}", e.getMessage());
            return null;
        }
    }
}
