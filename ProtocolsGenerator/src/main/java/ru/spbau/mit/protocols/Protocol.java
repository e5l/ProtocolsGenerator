package ru.spbau.mit.protocols;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import ru.spbau.mit.protocols.utils.UnionType;

public class Protocol<I> {
    private final Class<I> generator;

    public Protocol(Class<I> generator) {
        this.generator = generator;
    }

    public I getInstanceWithReceiver(Object receiver) throws IllegalAccessException, InstantiationException {
        UnionType<ProtocolType, I> result = new UnionType<>(generator.newInstance());
        result.getFirst().bind(receiver);
        return result.getSecond();
    }

    public UnionType<ProtocolType, I> getInstance() throws IllegalAccessException, InstantiationException {
        return new UnionType<ProtocolType, I>(generator.newInstance());
    }
}
