package ru.spbau.mit.protocols.benchmarks;

import caller.Caller;
import org.junit.Test;
import org.openjdk.jmh.infra.Blackhole;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooChildA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooClassA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooImplA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooInterfaceA;

public class ProtocolsBenchmarkTest {
    private final static String key =  "Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.";

    /* for test purpose only, don't reuse */
    private Blackhole blackhole = new Blackhole(key);

    private FooClassA classInstance = new FooClassA(blackhole);
    private FooClassA childInstance = new FooChildA(blackhole);
    private FooInterfaceA interfaceInstanceImpl = new FooImplA(blackhole);
    private FooInterfaceA interfaceInstanceLambda = () -> blackhole.consume(42);

    @Test
    public void indy() throws Exception {
        Caller.foo(classInstance);
        Caller.foo(childInstance);
        Caller.foo(interfaceInstanceImpl);
        Caller.foo(interfaceInstanceLambda);
    }

    @Test
    public void reflect() throws Exception {
        Caller.reflectFoo(classInstance);
        Caller.reflectFoo(childInstance);
        Caller.reflectFoo(interfaceInstanceImpl);
        Caller.reflectFoo(interfaceInstanceLambda);
    }
}