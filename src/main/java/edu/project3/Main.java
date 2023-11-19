package edu.project3;

import edu.project3.fileparsers.SimpleFileParser;
import java.nio.file.Path;

public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        LogAnalyzer logAnalyzer = new LogAnalyzer(args, new SimpleFileParser());
        Path report = logAnalyzer.analyze();

    }
}
