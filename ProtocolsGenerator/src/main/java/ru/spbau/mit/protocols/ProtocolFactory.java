package ru.spbau.mit.protocols;

import org.objectweb.asm.*;

import javax.activation.UnsupportedDataTypeException;
import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.objectweb.asm.Opcodes.*;
import static me.qmx.jitescript.util.CodegenUtils.*;

public class ProtocolFactory {
    private final static String RECEIVER_NAME = "receiver";

    public static <I> Protocol<I> makeGenerator(final int id, final Class<I> type) throws IllegalAccessException, InstantiationException, UnsupportedDataTypeException {
        if (!type.isInterface()) {
            throw new WrongMethodTypeException("Type should be an interface");
        }

        final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        final String name = type.getSimpleName();

        writer.visit(52, ACC_PUBLIC + ACC_FINAL + ACC_SUPER, String.format("ProtocolFactory%s%d", name, id),
                null, p(Object.class), new String[]{p(ProtocolType.class), p(type)});

        writer.visitSource(String.format("ProtocolFactory%s%d.java", name, id), null);

        makeReceiver(writer);
        makeInit(writer, name, id);
        makeBind(writer, type, name, id);

        Method[] methods = type.getMethods();
        for (Method method : methods) {
            makeMethod(writer, method, type, name, id);
        }

        writer.visitEnd();

        Class<?> result = new ClassLoader(ClassLoader.getSystemClassLoader()) {
            public Class defineClass(String name, byte[] code) {
                return super.defineClass(name, code, 0, code.length);
            }
        }.defineClass(String.format("ProtocolFactory%s%d", name, id), writer.toByteArray());

        return new Protocol<I>((Class<I>) result);
    }

    private static void makeReceiver(final ClassWriter writer) {
        final FieldVisitor fieldVisitor = writer.visitField(ACC_PRIVATE, RECEIVER_NAME, ci(Object.class), null, null);
        fieldVisitor.visitEnd();
    }

    private static <T> void makeBind(final ClassWriter writer, final Class<T> type, final String name, int id) {
        final MethodVisitor vMethod = writer.visitMethod(ACC_PUBLIC, "bind", sig(void.class, Object.class), null, null);
        vMethod.visitCode();

        final Label l0 = new Label();
        vMethod.visitLabel(l0);
        vMethod.visitVarInsn(ALOAD, 0);
        vMethod.visitVarInsn(ALOAD, 1);
        vMethod.visitFieldInsn(PUTFIELD, String.format("ProtocolFactory%s%d", name, id), "receiver", ci(Object.class));
        vMethod.visitInsn(RETURN);
        final Label l1 = new Label();
        vMethod.visitLabel(l1);
        vMethod.visitLocalVariable("this", String.format("LProtocolFactory%s%d;", name, id), null, l0, l1, 0);
        vMethod.visitLocalVariable("receiver", ci(Object.class), null, l0, l1, 1);

        vMethod.visitMaxs(0, 0);
        vMethod.visitEnd();
    }

    private static <T> void makeMethod(final ClassWriter writer, final Method method, final Class<T> type, final String name, final int id) throws UnsupportedDataTypeException {
        final String descriptor = sig(method.getReturnType(), method.getParameterTypes());
        final Type methodType = Type.getType(descriptor);
        final MethodVisitor vMethod = writer.visitMethod(ACC_PUBLIC, method.getName(), descriptor, null, null);

        vMethod.visitCode();

        final Label l0 = new Label();
        vMethod.visitLabel(l0);

        final Handle bootstrap = new Handle(Opcodes.H_INVOKESTATIC,
                p(ProtocolCallSite.class),
                "getBootstrap",
                sig(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, MethodType.class),
                false);

        // Push ProtocolCallSite instance
        vMethod.visitInvokeDynamicInsn("apply", sig(ProtocolCallSite.class), bootstrap, method.getName(), methodType);

        // Load receiver from field
        vMethod.visitVarInsn(ALOAD, 0);
        vMethod.visitFieldInsn(GETFIELD, String.format("ProtocolFactory%s%d", name, id), RECEIVER_NAME, ci(Object.class));

        // Push MethodHandle
        vMethod.visitMethodInsn(INVOKEVIRTUAL, p(ProtocolCallSite.class), "getMethod", sig(MethodHandle.class, Object.class), false);

        // Push arguments
        final Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Type paramType = Type.getType(parameters[i].getType());
            switch (paramType.getSort()) {
                case Type.INT:
                    vMethod.visitVarInsn(ILOAD, i + 1);
                    break;
                case Type.OBJECT:
                    vMethod.visitVarInsn(ALOAD, i + 1);
                    break;
                default:
                    throw new UnsupportedDataTypeException(paramType.toString());
            }
        }

        vMethod.visitMethodInsn(INVOKEVIRTUAL, p(MethodHandle.class), "invokeExact", methodType.getDescriptor(), false);

        Type returnType = methodType.getReturnType();
        switch (returnType.getSort()) {
            case Type.VOID:
                vMethod.visitInsn(RETURN);
                break;
            case Type.BOOLEAN:
            case Type.INT:
                vMethod.visitInsn(IRETURN);
                break;
            case Type.OBJECT:
                vMethod.visitInsn(ARETURN);
                break;
            default:
                throw new UnsupportedDataTypeException(returnType.toString());
        }

        final Label l1 = new Label();
        vMethod.visitLabel(l1);
        vMethod.visitLocalVariable("this", String.format("LProtocolFactory%s%d;", name, id), null, l0, l1, 0);

        for (int i = 0; i < parameters.length; i++) {
            vMethod.visitLocalVariable(parameters[i].getName(), ci(parameters[i].getType()), null, l0, l1, i + 1);
        }

        vMethod.visitMaxs(0, 0);
        vMethod.visitEnd();
    }

    private static void makeInit(final ClassWriter writer, final String name, final int id) {
        MethodVisitor vMethod = writer.visitMethod(ACC_PUBLIC, "<init>", sig(void.class), null, null);
        vMethod.visitCode();

        Label l0 = new Label();
        vMethod.visitLabel(l0);
        vMethod.visitVarInsn(ALOAD, 0);
        vMethod.visitMethodInsn(INVOKESPECIAL, p(Object.class), "<init>", sig(void.class), false);
        vMethod.visitInsn(RETURN);

        Label l1 = new Label();
        vMethod.visitLabel(l1);
        vMethod.visitLocalVariable("this", String.format("LProtocolFactory%s%d;", name, id), null, l0, l1, 0);
        vMethod.visitMaxs(0, 0);
        vMethod.visitEnd();
    }
}
