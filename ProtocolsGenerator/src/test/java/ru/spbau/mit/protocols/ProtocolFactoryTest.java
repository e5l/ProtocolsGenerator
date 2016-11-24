package ru.spbau.mit.protocols;

import org.junit.Test;
import ru.spbau.mit.protocols.helpers.SimpleIntPredicate;
import ru.spbau.mit.protocols.helpers.SimpleRunnable;

import java.util.function.IntPredicate;

import static org.junit.Assert.*;

public class ProtocolFactoryTest {
    @Test
    public void makeGenerator() throws Exception {
        final Runnable runnable = ProtocolFactory
                .makeGenerator(0, Runnable.class)
                .getInstanceWithReceiver(new SimpleRunnable());

        runnable.run();

        IntPredicate isPositive = ProtocolFactory
                .makeGenerator(1, IntPredicate.class)
                .getInstanceWithReceiver(new SimpleIntPredicate());

        assertTrue(isPositive.test(10));
        assertFalse(isPositive.test(-10));
    }

}