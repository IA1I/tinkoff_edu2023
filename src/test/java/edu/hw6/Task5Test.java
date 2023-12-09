package edu.hw6;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {
    @Test
    void shouldReturnArrayOfLongIdsOfTopStories() {
        long[] actual = Task5.hackerNewsTopStories();
        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldReturnTitleOfIdStory() {
        long id = 37570037L;
        String expected = "JDK 21 Release Notes";
        String actual = Task5.news(id);

        assertThat(actual).isEqualTo(expected);
    }
}
