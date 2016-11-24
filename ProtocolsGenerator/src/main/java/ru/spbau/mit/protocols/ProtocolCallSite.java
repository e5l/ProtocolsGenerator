package ru.spbau.mit.protocols;

import java.lang.invoke.*;

public class ProtocolCallSite {
    private final MethodHandles.Lookup lookup;
    private final String callableName;
    private final MethodType callableType;

    private Class<?> cache;
    private MethodHandle handle;

    public ProtocolCallSite(MethodHandles.Lookup lookup, String name, MethodType type, String callableName, MethodType callableType) {
        this.lookup = lookup;
        this.callableName = callableName;
        this.callableType = callableType;
    }

    public MethodHandle getMethod(Object receiver) throws Throwable {
        Class<?> receiverClass = receiver.getClass();
        if (cache == null || cache != receiverClass) {
            cache = receiverClass;
            handle = lookup.findVirtual(receiver.getClass(), callableName, callableType).bindTo(receiver);
        }
        return handle;
    }

    public static CallSite getBootstrap(MethodHandles.Lookup lookup, String name, MethodType type, String callableName, MethodType callableType) {
        final ProtocolCallSite instance = new ProtocolCallSite(lookup, name, type, callableName, callableType);
        return new ConstantCallSite(MethodHandles.constant(ProtocolCallSite.class, instance));
    }
}
