package edu.hw6.task2;

import java.nio.file.Path;

public class Filename {
    private static final char POINT = '.';
    private String absolutePath;
    private String name;
    private String extension;

    private Filename(String absolutePath, String name, String extension) {
        this.absolutePath = absolutePath;
        this.name = name;
        this.extension = extension;
    }

    public static Filename of(Path file) {
        String absolutePath = getAbsolutePath(file);
        String fullName = file.getFileName().toString();
        String name = getName(fullName);
        String extension = getExtension(fullName);

        return new Filename(absolutePath, name, extension);
    }

    private static String getAbsolutePath(Path file) {
        String absolutePath = file.toAbsolutePath().toString();
        int indexOfSlash = absolutePath.lastIndexOf('\\');

        return absolutePath.substring(0, indexOfSlash + 1);
    }

    private static String getName(String fullName) {
        int indexOfPoint = fullName.lastIndexOf(POINT);

        return fullName.substring(0, indexOfPoint);
    }

    private static String getExtension(String fullName) {
        int indexOfPoint = fullName.lastIndexOf(POINT);

        return fullName.substring(indexOfPoint);
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }
}
