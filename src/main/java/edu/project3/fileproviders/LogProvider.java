package edu.project3.fileproviders;

import java.nio.file.Path;
import java.util.List;

public interface LogProvider {
    List<Path> provide(String path);

    static LogProvider getInstance(String path) {
        if (path != null && path.contains("https://")) {
            return new HttpLogProvider();
        } else {
            return new LocalFileLogProvider();
        }
    }
}
