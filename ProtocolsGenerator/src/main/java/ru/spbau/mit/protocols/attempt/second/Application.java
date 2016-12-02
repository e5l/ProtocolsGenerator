package ru.spbau.mit.protocols.attempt.second;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Application {

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final byte[] result = Generator.generateCaller(1);
        String outputPath = "~/Caller.class";
        if (args.length > 0) {
            outputPath = args[0];
        }

        FileOutputStream output = new FileOutputStream(new File(outputPath));
        output.write(result);
        output.close();
    }

}
