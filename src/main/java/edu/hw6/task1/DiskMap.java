package edu.hw6.task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PATH_TO_DIRECTORY =
        "E:\\practice\\TINKOFF\\practice\\tinkoff_edu2023\\src\\main\\resources\\storage for hw6 task1\\";
    private static final String FILE_EXTENSION = ".txt";
    private static final String SEPARATOR = ":";
    private static final String LINE_BREAK = System.getProperty("line.separator");
    private UUID id;
    private String storageFilename;
    private Path storageFile;
    private Map<String, String> map = new HashMap<>();

    public DiskMap(Path storageFile) {
        this.storageFilename = getFilename(storageFile);
        loadFromFile(storageFile);
        checkFilenameForId(storageFilename);
    }

    private void checkFilenameForId(String storageFilename) {
        try {
            id = UUID.fromString(storageFilename);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid file name");
            throw new IllegalArgumentException(e);
        }
    }

    public DiskMap(String storageFilename) {
        this(Path.of(storageFilename));
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return map.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        String oldNode = key + SEPARATOR + map.get(key);
        String newNode = key + SEPARATOR + value;
        saveNode(newNode, oldNode);
        return map.put(key, value);
    }

    @Override
    public String remove(Object key) {
        if (map.containsKey(key)) {
            String node = key.toString() + SEPARATOR + map.get(key);
            removeNode(node);
            return map.remove(key);
        }
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (var entry : m.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            put(key, value);
        }
    }

    @Override
    public void clear() {
        writeToStorage("");
        map.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return map.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return map.entrySet();
    }

    private Path createStorageFile() {
        Path storageDirectory = Path.of(PATH_TO_DIRECTORY);
        Path storage = getStorageFile(storageDirectory);

        try {
            Files.createFile(storage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return storage;
    }

    private Path getStorageFile(Path storageDirectory) {
        Path storage;
        if (Files.exists(storageDirectory)) {
            storage = Path.of(PATH_TO_DIRECTORY + storageFilename + FILE_EXTENSION);
        } else {
            storage = Path.of(storageFilename + FILE_EXTENSION);
        }
        return storage;
    }

    private void removeNode(String node) {
        String storageMap = readFromStorage();
        String[] nodes = storageMap.split(LINE_BREAK);
        StringBuilder newStorageMap = new StringBuilder();
        for (var line : nodes) {
            if (!line.equals(node)) {
                newStorageMap.append(line)
                    .append(LINE_BREAK);
            }
        }
        storageMap = newStorageMap.toString();

        writeToStorage(storageMap);
    }

    private void saveNode(String newNode, String oldNode) {
        String storageMap = readFromStorage();
        if (storageMap.contains(oldNode)) {
            storageMap = storageMap.replace(oldNode, newNode);
        } else {
            storageMap += newNode + LINE_BREAK;
        }
        writeToStorage(storageMap);
    }

    private void writeToStorage(String storageMap) {
        try (BufferedWriter bw = Files.newBufferedWriter(storageFile, StandardCharsets.UTF_8)) {
            bw.write(storageMap);
        } catch (IOException e) {
            LOGGER.error("Something went wrong while writing the file");
            throw new RuntimeException(e);
        }

        LOGGER.info("Successful write to file");
    }

    private String readFromStorage() {
        StringBuilder storageMap = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(storageFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                storageMap.append(line)
                    .append(LINE_BREAK);
            }
        } catch (IOException e) {
            LOGGER.error("Something went wrong while reading the file");
            throw new RuntimeException(e);
        }
        LOGGER.info("Successful reading from file");
        return storageMap.toString();
    }

    public void loadFromFile(String path) {
        loadFromFile(Path.of(path));
    }

    public void loadFromFile(Path file) {
        storageFile = checkInputFile(file);
        storageFilename = getFilename(storageFile);

        map.clear();
        String storage = readFromStorage();
        String[] nodes = storage.split(LINE_BREAK);
        for (String node : nodes) {
            if (!node.isEmpty()) {
                String[] keyValue = parseNode(node);
                map.put(keyValue[0], keyValue[1]);
            }
        }
    }

    private static Path checkInputFile(Path file) {
        if (!Files.exists(file)) {
            LOGGER.error("File {} does not exist", file.getFileName().toString());
            throw new IllegalArgumentException("File does not exist");
        } else if (!Files.isRegularFile(file)) {
            LOGGER.error("The path {} is not to the file", file.toAbsolutePath().toString());
            throw new IllegalArgumentException("The path is not to the file");
        } else {
            LOGGER.info("Successful file verification");
            return file;
        }
    }

    private String[] parseNode(String node) {
        String[] keyValue = node.split(SEPARATOR);
        if (keyValue.length != 2) {
            LOGGER.error("Incorrect recording format");
            throw new IllegalArgumentException();
        }

        return keyValue;
    }

    private String getFilename(Path storageFile) {
        String filename = storageFile.getFileName().toString();

        return filename.substring(0, filename.length() - FILE_EXTENSION.length());
    }
}
