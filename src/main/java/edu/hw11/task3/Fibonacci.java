package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Fibonacci implements ByteCodeAppender {
    @Override
    public Size apply(
        MethodVisitor methodVisitor,
        Implementation.Context context,
        MethodDescription methodDescription
    ) {
        if (!methodDescription.getReturnType().asErasure().represents(long.class)) {
            throw new IllegalArgumentException(methodDescription + " must return long");
        }
        StackManipulation.Size operandStackSize = new FibonacciStackManipulation().apply(methodVisitor, context);
        return new Size(
            operandStackSize.getMaximalSize(),
            methodDescription.getStackSize()
        );
    }
}
