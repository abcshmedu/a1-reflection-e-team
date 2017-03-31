/*
 * Wolfgang Gabler
 *
 * Software: Mac OS X 10.12, Oracle Java 1.8.0_111 SE
 * System: Intel Core i7-4850HQ, 16 GByte RAM
 */
package edu.hm.wgabler.limmer.reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

/**
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @since 30/03/2017
 */
public class Test {

    int foo() {
        return 42;
    }

    public static void main(String... args) {
        final Test test = new Test();
        Stream.of(Test.class.getDeclaredMethods())
                .filter(m -> m.getParameterCount() == 0)
                .forEach(m -> {
                    try {
                        System.out.println(m.invoke(test).getClass());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }

}
