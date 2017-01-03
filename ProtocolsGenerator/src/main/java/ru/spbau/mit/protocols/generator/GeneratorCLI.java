package ru.spbau.mit.protocols.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GeneratorCLI {
    public static final String DEFAULT_NAME = "Caller.class";

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final byte[] result = Generator.generateCaller();
        String outputPath = DEFAULT_NAME;
        if (args.length > 0) {
            outputPath = args[0];
        }

        FileOutputStream output = new FileOutputStream(new File(outputPath));
        output.write(result);
        output.close();
    }

}
