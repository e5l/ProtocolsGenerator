package ru.spbau.mit.protocols.benchmarks;

import org.openjdk.jmh.profile.LinuxPerfAsmProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Date;

public class BenchmarksExecutor {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ProtocolsBenchmark.class.getSimpleName())
                .include(ProtocolsBenchmarkBatches.class.getSimpleName())
                .warmupIterations(20)
                .measurementIterations(20)
                .forks(1)
                .addProfiler(LinuxPerfAsmProfiler.class)
                .jvmArgs("-XX:+UnlockDiagnosticVMOptions",
                        "-XX:+LogCompilation",
                        String.format("-XX:LogFile=tests/perf_log_%s_.xml", new Date().toString()),
                        "-XX:+PrintAssembly",
                        "-XX:+PrintInterpreter",
                        "-XX:+PrintNMethods",
                        "-XX:+PrintNativeNMethods",
                        "-XX:+PrintSignatureHandlers",
                        "-XX:+PrintAdapterHandlers",
                        "-XX:+PrintStubCode",
                        "-XX:+PrintCompilation",
                        "-XX:+PrintInlining",
                        "-XX:+TraceClassLoading",
                        "-XX:PrintAssemblyOptions=syntax")
                .threads(1)
                .build();

        new Runner(options).run();
    }
}
