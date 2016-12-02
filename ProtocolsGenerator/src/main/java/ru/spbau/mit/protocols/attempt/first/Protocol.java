package ru.spbau.mit.protocols.attempt.first;

import ru.spbau.mit.protocols.attempt.first.utils.UnionType;

public class Protocol<I> {
    private final Class<?> generator;

    public Protocol(Class<?> generator) {
        this.generator = generator;
    }

    public I getInstanceWithReceiver(Object receiver) throws IllegalAccessException, InstantiationException {
        UnionType<ProtocolType, I> result = new UnionType<>(generator.newInstance());
        result.getFirst().bind(receiver);
        return result.getSecond();
    }

    public UnionType<ProtocolType, I> getInstance() throws IllegalAccessException, InstantiationException {
        return new UnionType<>(generator.newInstance());
    }
}
