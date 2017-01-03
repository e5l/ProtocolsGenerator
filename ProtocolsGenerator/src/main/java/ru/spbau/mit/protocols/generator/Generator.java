package ru.spbau.mit.protocols.generator;

import org.objectweb.asm.*;

import java.lang.invoke.*;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;
import static me.qmx.jitescript.util.CodegenUtils.*;

public class Generator {
    private static final int CODE_VERSION = 52;

    /**
     * Generate and and load class
     *
     * @return loaded class from generateCaller() method
     */
    public static Class<?> generateCallerClass() {
        byte[] bytes = generateCaller();

        return new ClassLoader(ClassLoader.getSystemClassLoader()) {
            public Class defineClass(final String name, byte[] code) {
                return super.defineClass(name, code, 0, code.length);
            }
        }.defineClass("Caller", bytes);
    }

    /**
     * Generate raw classfile with all available backends
     *
     * @return raw classfile
     */
    public static byte[] generateCaller() {
        final String callerPackage = "caller/Caller";
        final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        writer.visit(CODE_VERSION,
                ACC_PUBLIC + ACC_SUPER,
                callerPackage,
                null,
                p(Object.class),
                null);

        writer.visitSource("Caller.java", null);
        makeInit(writer);
        makeMethodHandlerCaller(writer);
        makeReflectCaller(writer);
        writer.visitEnd();

        return writer.toByteArray();
    }

    /**
     * Generate indy backend based on MethodHandle
     *
     * @param writer
     */
    private static void makeMethodHandlerCaller(final ClassWriter writer) {
        final MethodVisitor vMethod = writer.visitMethod(ACC_PUBLIC + ACC_STATIC, "foo", sig(void.class, Object.class), null, null);
        vMethod.visitCode();

        final Label l0 = new Label();
        vMethod.visitLabel(l0);

        final Handle bootstrap = new Handle(Opcodes.H_INVOKESTATIC,
                p(ProtocolCallSite.class),
                "getBootstrap",
                sig(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, MethodType.class),
                false);

        vMethod.visitInvokeDynamicInsn("apply", sig(ProtocolCallSite.class), bootstrap, "bar", Type.getType(sig(void.class)));

        vMethod.visitVarInsn(ALOAD, 0);
        vMethod.visitMethodInsn(INVOKEVIRTUAL, p(ProtocolCallSite.class), "getMethod", sig(MethodHandle.class, Object.class), false);
        vMethod.visitMethodInsn(INVOKEVIRTUAL, p(MethodHandle.class), "invokeExact", sig(void.class), false);

        vMethod.visitInsn(RETURN);
        final Label l1 = new Label();
        vMethod.visitLabel(l1);
        vMethod.visitLocalVariable("receiver", ci(Object.class), null, l0, l1, 0);
        vMethod.visitMaxs(0, 0);

        vMethod.visitEnd();
    }

    /**
     * Generate reflective backend
     *
     * @param writer
     */
    private static void makeReflectCaller(final ClassWriter writer) {
        final MethodVisitor vMethod = writer.visitMethod(ACC_PUBLIC + ACC_STATIC, "reflectFoo", sig(void.class, Object.class), null, null);
        vMethod.visitCode();

        final Label l0 = new Label();
        vMethod.visitLabel(l0);

        final Handle bootstrap = new Handle(Opcodes.H_INVOKESTATIC,
                p(ProtocolCallSite.class),
                "getBootstrap",
                sig(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, MethodType.class),
                false);

        vMethod.visitInvokeDynamicInsn("apply", sig(ProtocolCallSite.class), bootstrap, "bar", Type.getType(sig(void.class)));

        vMethod.visitVarInsn(ALOAD, 0);
        vMethod.visitMethodInsn(INVOKEVIRTUAL, p(ProtocolCallSite.class), "getReflectMethod", sig(Method.class, Object.class), false);

        vMethod.visitVarInsn(ALOAD, 0);
        vMethod.visitInsn(ICONST_0);
        vMethod.visitTypeInsn(ANEWARRAY, p(Object.class));
        vMethod.visitMethodInsn(INVOKEVIRTUAL, p(Method.class), "invoke", sig(Object.class, Object.class, Object[].class), false);

        vMethod.visitInsn(RETURN);
        final Label l1 = new Label();
        vMethod.visitLabel(l1);
        vMethod.visitLocalVariable("receiver", ci(Object.class), null, l0, l1, 0);
        vMethod.visitMaxs(0, 0);

        vMethod.visitEnd();
    }

    /**
     * Generate init method
     *
     * @param writer class writer
     */
    private static void makeInit(final ClassWriter writer) {
        final MethodVisitor vMethod = writer.visitMethod(ACC_PUBLIC, "<init>", sig(void.class), null, null);
        vMethod.visitCode();

        final Label l0 = new Label();
        vMethod.visitLabel(l0);
        vMethod.visitVarInsn(ALOAD, 0);
        vMethod.visitMethodInsn(INVOKESPECIAL, p(Object.class), "<init>", sig(void.class), false);
        vMethod.visitInsn(RETURN);

        final Label l1 = new Label();
        vMethod.visitLabel(l1);
        vMethod.visitLocalVariable("this", "Lself;", null, l0, l1, 0);
        vMethod.visitMaxs(0, 0);
        vMethod.visitEnd();
    }
}
