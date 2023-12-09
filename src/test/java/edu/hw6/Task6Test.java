package edu.hw6;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class Task6Test {
    @Test
    void test() {
        String actual = Task6.scanPorts();
        assertThat(actual).isNotEmpty();
        assertThat(actual).contains("Протокол  Порт   Сервис\n");
    }

}
