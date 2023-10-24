package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Nested
    @DisplayName("1. Шифр Атбаш")
    class TestTask1{

        @Test
        void shouldThrowIllegalArgumentExceptionForNullString(){
            assertThrows(IllegalArgumentException.class, () -> Task1.atbash(null));
        }

        @ParameterizedTest
        @CsvSource({
            "Hello world!, Svool dliow!",
            "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler, Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi",
            "1234567890-=.!?/'[]{}()\\;:<>*&^%$#@, 1234567890-=.!?/'[]{}()\\;:<>*&^%$#@"
        })
        void shouldCipherString(final String input, final String expected){
            String actual = Task1.atbash(input);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
