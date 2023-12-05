package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilesFinder extends RecursiveTask<List<Path>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private int lowerSizeLimit;
    private int upperSizeLimit;
    private String extension;

    private Path path;

    public FilesFinder(Path path) {
        this(0, Integer.MAX_VALUE, "", path);
    }

    public FilesFinder(int lowerSizeLimit, int upperSizeLimit, String extension, Path path) {
        if (path == null) {
            LOGGER.error("Input path is null");
            throw new IllegalArgumentException();
        }
        this.lowerSizeLimit = lowerSizeLimit;
        this.upperSizeLimit = upperSizeLimit;
        this.extension = extension;
        this.path = path;
    }

    @Override
    protected List<Path> compute() {
        List<Path> files = new ArrayList<>();
        List<FilesFinder> finders = new ArrayList<>();
        try (Stream<Path> paths = Files.list(path)) {
            paths.forEach(p -> {
                checkFile(p, files);
                if (Files.isDirectory(p)) {
                    finders.add(new FilesFinder(lowerSizeLimit, upperSizeLimit, extension, p));
                    finders.getLast().fork();
                    LOGGER.info("Added new task with directory: {}", p);
                }
            });
        } catch (IOException e) {
            LOGGER.error("Something went wrong: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        finders.forEach(finder -> files.addAll(finder.join()));

        return files;
    }

    private void checkFile(Path path, List<Path> files) {
        long size;
        try {
            size = Files.size(path);
        } catch (IOException e) {
            size = 0;
            LOGGER.error("Something went wrong while getting file size: {}", e.getMessage());
        }
        if (Files.isRegularFile(path) && (size >= lowerSizeLimit) && (size <= upperSizeLimit)
            && path.toString().contains(extension)) {
            files.add(path);
            LOGGER.info("Added new file: {}", path);
        }
    }
}
