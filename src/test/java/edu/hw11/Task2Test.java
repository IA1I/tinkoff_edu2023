package edu.hw11;

import edu.hw11.task2.ArithmeticUtils;
import edu.hw11.task2.ClassForMulti;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.*;

public class Task2Test {
    @Test
    void shouldChangeMethodLogicFromSumToMulti() throws Exception {
        ArithmeticUtils actual = new ByteBuddy()
            .subclass(ArithmeticUtils.class)
            .method(named("sum")).intercept(MethodDelegation.to(ClassForMulti.class))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded()
            .newInstance();
        assertThat(actual.sum(1, 0)).isEqualTo(0);
        assertThat(actual.sum(5, -3)).isEqualTo(-15);
    }
}
