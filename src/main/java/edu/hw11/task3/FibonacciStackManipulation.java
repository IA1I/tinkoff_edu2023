package edu.hw11.task3;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.F_SAME;
import static org.objectweb.asm.Opcodes.I2L;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.ICONST_2;
import static org.objectweb.asm.Opcodes.IF_ICMPGT;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.ISUB;
import static org.objectweb.asm.Opcodes.LADD;
import static org.objectweb.asm.Opcodes.LRETURN;

public class FibonacciStackManipulation implements StackManipulation {

    private static final String FUNCTION_NAME = "fib";
    private static final String DESCRIPTOR = "(I)J";
    public static final int SIZE = 5;

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        Label l1 = new Label();
        Label l2 = new Label();
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitJumpInsn(IF_ICMPGT, l1);
        methodVisitor.visitLabel(l2);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(I2L);
        methodVisitor.visitInsn(LRETURN);
        methodVisitor.visitLabel(l1);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitInsn(ISUB);
        methodVisitor.visitMethodInsn(
            INVOKEVIRTUAL,
            context.getInstrumentedType().getInternalName(),
            FUNCTION_NAME,
            DESCRIPTOR,
            false
        );
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_2);
        methodVisitor.visitInsn(ISUB);
        methodVisitor.visitMethodInsn(
            INVOKEVIRTUAL,
            context.getInstrumentedType().getInternalName(),
            FUNCTION_NAME,
            DESCRIPTOR,
            false
        );
        methodVisitor.visitInsn(LADD);
        methodVisitor.visitInsn(LRETURN);
        methodVisitor.visitEnd();
        return new Size(0, SIZE);
    }
}
