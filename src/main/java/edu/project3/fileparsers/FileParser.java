package edu.project3.fileparsers;

import java.nio.file.Path;
import java.util.List;

public interface FileParser {
    List<Log> parse(List<Path> files);
}
