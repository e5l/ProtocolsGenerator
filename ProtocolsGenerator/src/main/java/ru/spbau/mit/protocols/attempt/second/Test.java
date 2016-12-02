package ru.spbau.mit.protocols.attempt.second;

import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class<?> aClass = Generator.generateCallerClass(0);
        aClass.getDeclaredMethods()[1].invoke(null, new Example());

        System.out.println("OK");
    }
}
