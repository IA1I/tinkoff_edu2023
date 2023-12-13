package edu.hw10;

import edu.hw10.task2.CacheProxy;
import edu.hw10.task2.IntInterface;
import edu.hw10.task2.IntInterfaceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    static Path path = Path.of("test.txt");

    @Test
    void shouldCreateProxy() {
        IntInterface intInterface = new IntInterfaceImpl();
        IntInterface proxy = CacheProxy.create(intInterface, IntInterfaceImpl.class, path);
        assertThat(proxy.get(1)).isEqualTo(6);
        assertThat(proxy.get(2)).isEqualTo(7);
    }

    @Test
    void shouldSaveInDisk() throws IOException {
        List<String> expected = List.of("getWithCacheOnDisk[1] : -4", "getWithCacheOnDisk[2] : -3");
        IntInterface intInterface = new IntInterfaceImpl();
        IntInterface proxy = CacheProxy.create(intInterface, IntInterfaceImpl.class, path);
        assertThat(proxy.getWithCacheOnDisk(1)).isEqualTo(-4);
        assertThat(proxy.getWithCacheOnDisk(1)).isEqualTo(-4);
        assertThat(proxy.getWithCacheOnDisk(2)).isEqualTo(-3);
        assertThat(proxy.getWithCacheOnDisk(2)).isEqualTo(-3);
        List<String> actual = Files.readAllLines(path);
        assertThat(actual).containsExactlyElementsOf(expected);
        Files.delete(path);
    }
}
