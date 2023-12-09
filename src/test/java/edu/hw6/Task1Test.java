package edu.hw6;

import edu.hw6.task1.DiskMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    static Path file;
    static Map<String, String> ordinaryMap;
    static List<String> entries;

    @BeforeAll
    static void preparation() {
        try {
            file = Files.createFile(Path.of("10599643-ca3b-4e63-ab64-7c72180c5c37.txt"));
        } catch (IOException e) {

        }
        ordinaryMap = new HashMap<>();
        ordinaryMap.put("a", "1");
        ordinaryMap.put("b", "2");
        ordinaryMap.put("c", "3");
        ordinaryMap.put("d", "4");

        entries = new ArrayList<>();
        entries.add("a:1");
        entries.add("b:2");
        entries.add("c:3");
        entries.add("d:4");
    }

    @Test
    void shouldPutEntriesToMap() throws IOException {
        DiskMap map = new DiskMap(file);
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");

        assertThat(map.containsValue("3")).isTrue();
        assertThat(map.size()).isEqualTo(ordinaryMap.size());
        assertThat(map).containsExactlyInAnyOrderEntriesOf(ordinaryMap);

        List<String> lines = Files.readAllLines(file);
        assertThat(lines).containsExactlyInAnyOrderElementsOf(entries);
    }

    @Test
    void shouldRemoveEntryFromMap() throws IOException {
        DiskMap map = new DiskMap(file);
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        map.put("e", "5");
        map.remove("e");

        List<String> lines = Files.readAllLines(file);
        assertThat(lines).containsExactlyInAnyOrderElementsOf(entries);
    }

    @Test
    void shouldPutMapToDiskMap() throws IOException {
        DiskMap map = new DiskMap(file);
        map.putAll(ordinaryMap);

        assertThat(map).containsExactlyInAnyOrderEntriesOf(ordinaryMap);

        List<String> lines = Files.readAllLines(file);
        assertThat(lines).containsExactlyInAnyOrderElementsOf(entries);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNotExistingClass() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new DiskMap("d4de2799-2b14-4146-ab4d-8abc5586152c.txt")
        );
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForPathToDirectory() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new DiskMap(File.separator + "test")
        );
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForIncorrectFormatOfEntries() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new DiskMap("pom.xml")
        );
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForWrongFilename() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new DiskMap("2.txt")
        );
    }

    @Test
    void shouldClearMap() throws IOException {
        DiskMap map = new DiskMap(file);
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        map.put("e", "5");
        map.clear();

        List<String> lines = Files.readAllLines(file);
        assertThat(lines.isEmpty()).isTrue();
    }

    @AfterAll
    static void ending() {
        try {
            Files.delete(file);
        } catch (IOException e) {

        }
    }
}
