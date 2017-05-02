package bugs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by user on 5/2/17.
 */
public class Xstacksize {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method x = A0.class.getMethod("foo");
        Object invoke = x.invoke(new A0());
    }
}
