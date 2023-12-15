package edu.hw11;

import edu.hw11.task3.Fibonacci;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.Implementation;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.objectweb.asm.Opcodes.*;

public class Task3Test {
    @Test
    void should()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> dynamicClass = new ByteBuddy().subclass(Object.class)
            .name("Fibonacci")
            .defineMethod("fib", long.class, Opcodes.ACC_PUBLIC)
            .withParameter(int.class)
            .intercept(new Implementation.Simple(new Fibonacci()))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
        Object instance = dynamicClass.getDeclaredConstructor().newInstance();
        long[] expected = {0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L};
        for (int i = 0; i < expected.length; i++) {
            assertThat(dynamicClass.getMethod("fib", int.class).invoke(instance, i)).isEqualTo(expected[i]);
        }
    }
}
