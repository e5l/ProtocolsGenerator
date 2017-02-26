package main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Hello {

    public int myAwesomeMethod(int a, int b, int c, int d) { return a + b + c + d; }

    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method myAwesomeMethod = Hello.class.getMethod("myAwesomeMethod", int.class, int.class, int.class, int.class);

        Object[] objects = {1, 2, 3, 4};

        int i = (int) myAwesomeMethod.invoke(new Hello(), objects);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        new Hello().test();
    }

}
