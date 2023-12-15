package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class Task1Test {
    @Test
    void shouldCreateNewClassWithToStringMethod() throws Exception {
        String expected = "Hello World!";
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString")).intercept(FixedValue.value(expected))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
        String actual = dynamicType.getDeclaredConstructor().newInstance().toString();

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
