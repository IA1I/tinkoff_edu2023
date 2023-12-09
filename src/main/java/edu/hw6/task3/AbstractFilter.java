package edu.hw6.task3;

import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

@FunctionalInterface
public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    default AbstractFilter and(AbstractFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter is null");
        }
        return (path) -> this.accept(path) && filter.accept(path);
    }

    static AbstractFilter largerThan(int size) {
        return entry -> Files.size(entry) > size;
    }

    static AbstractFilter globMatches(String extension) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + extension);
        return entry -> matcher.matches(entry.getFileName());
    }

    static AbstractFilter regexContains(String regex) {
        return entry -> entry.getFileName().toString().matches("^.*" + regex + ".*$");
    }

    static AbstractFilter magicNumber(byte... bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Bytes is null");
        }
        return entry -> {
            byte[] file;
            try {
                file = Files.readAllBytes(entry);
            } catch (AccessDeniedException e) {
                return false;
            }
            if (file.length < bytes.length) {
                return false;
            }
            for (int i = 0; i < bytes.length; i++) {
                if (file[i] != bytes[i]) {
                    return false;
                }
            }

            return true;
        };
    }
}
