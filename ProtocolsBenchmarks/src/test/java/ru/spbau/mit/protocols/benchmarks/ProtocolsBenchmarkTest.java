package ru.spbau.mit.protocols.benchmarks;

import caller.Caller;
import org.junit.Test;
import org.openjdk.jmh.infra.Blackhole;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooChild;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooClass;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooImpl;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooInterface;

import static org.junit.Assert.*;

public class ProtocolsBenchmarkTest {
    private final static String key =  "Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.";

    /* for test purpose only, don't reuse */
    private Blackhole blackhole = new Blackhole(key);

    private FooClass classInstance = new FooClass(blackhole);
    private FooClass childInstance = new FooChild(blackhole);
    private FooInterface interfaceInstanceImpl = new FooImpl(blackhole);
    private FooInterface interfaceInstanceLambda = () -> blackhole.consume(42);

    @Test
    public void indy() throws Exception {
        Caller.foo(classInstance);
        Caller.foo(childInstance);
        Caller.foo(interfaceInstanceImpl);
//        fail
//        Caller.foo(interfaceInstanceLambda);
    }

    @Test
    public void reflect() throws Exception {
        Caller.reflectFoo(classInstance);
        Caller.reflectFoo(childInstance);
        Caller.reflectFoo(interfaceInstanceImpl);
        Caller.reflectFoo(interfaceInstanceLambda);
    }
}