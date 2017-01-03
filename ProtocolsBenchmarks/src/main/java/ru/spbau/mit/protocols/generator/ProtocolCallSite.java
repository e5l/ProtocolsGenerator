package ru.spbau.mit.protocols.generator;

import java.lang.invoke.*;
import java.lang.reflect.Method;

public class ProtocolCallSite {
    private final MethodHandles.Lookup lookup;
    private final String callableName;
    private final MethodType callableType;

    private Class<?> cache;
    private MethodHandle handle;
    private Method reflectMethod;

    public ProtocolCallSite(MethodHandles.Lookup lookup, String name, MethodType type, String callableName, MethodType callableType) {
        this.lookup = lookup;
        this.callableName = callableName;
        this.callableType = callableType;
    }

    public MethodHandle getMethod(Object receiver) throws Throwable {
        Class<?> receiverClass = receiver.getClass();
        if (cache == null || cache != receiverClass) {
            cache = receiverClass;
            setupAccessible(receiverClass);
            handle = lookup.findVirtual(receiverClass, callableName, callableType).bindTo(receiver);
        }
        return handle;
    }

    public Method getReflectMethod(Object receiver) throws NoSuchMethodException {
        Class<?> receiverClass = receiver.getClass();
        if (cache == null || cache != receiverClass) {
            cache = receiverClass;
            reflectMethod = setupAccessible(receiverClass);
        }

        return reflectMethod;
    }

    public static CallSite getBootstrap(MethodHandles.Lookup lookup, String name, MethodType type, String callableName, MethodType callableType) {
        final ProtocolCallSite instance = new ProtocolCallSite(lookup, name, type, callableName, callableType);
        return new ConstantCallSite(MethodHandles.constant(ProtocolCallSite.class, instance));
    }


    public Method setupAccessible(final Class<?> cls) throws NoSuchMethodException {
        final Method method = cls.getDeclaredMethod(callableName, callableType.parameterArray());
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }

        return method;
    }
}
