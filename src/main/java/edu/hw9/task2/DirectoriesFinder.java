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

public class DirectoriesFinder extends RecursiveTask<List<Path>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUMBER_OF_FILES_IN_DIRECTORY = 5;
    private Path path;

    public DirectoriesFinder(Path path) {
        if (path == null) {
            LOGGER.error("Input path is null");
            throw new IllegalArgumentException();
        }
        this.path = path;
    }

    @Override
    protected List<Path> compute() {
        List<Path> directories = new ArrayList<>();
        List<DirectoriesFinder> finders = new ArrayList<>();
        try (Stream<Path> pathsForFiles = Files.list(path);
             Stream<Path> pathsForDirectories = Files.list(path)) {
            isRightDirectory(pathsForFiles, directories);
            pathsForDirectories.forEach(p -> {
                if (Files.isDirectory(p)) {
                    finders.add(new DirectoriesFinder(p));
                    finders.getLast().fork();
                    LOGGER.info("Added new task with directory: {}", p);
                }
            });
        } catch (IOException e) {
            LOGGER.error("Something went wrong: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        finders.forEach(finder -> directories.addAll(finder.join()));

        return directories;
    }

    private void isRightDirectory(Stream<Path> paths, List<Path> directories) {
        long filesNumber = paths.filter(Files::isRegularFile).count();
        if (filesNumber >= NUMBER_OF_FILES_IN_DIRECTORY) {
            directories.add(path);
            LOGGER.info("Added new directory: {} with {} files", path, filesNumber);
        }
    }
}
