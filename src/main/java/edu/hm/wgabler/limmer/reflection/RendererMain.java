/*
 * Wolfgang Gabler
 *
 * Software: Mac OS X 10.12, Oracle Java 1.8.0_111 SE
 * System: Intel Core i7-4850HQ, 16 GByte RAM
 */
package edu.hm.wgabler.limmer.reflection;

import java.util.Date;

/**
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @since 30/03/2017
 */
public class RendererMain {

    public static void main(String... args) {

        final SomeClass someClass = new SomeClass(42);
        final Renderer renderer = new Renderer(someClass);

        System.out.println(renderer.render());
    }

    static class SomeClass {

        @RenderMe(with = "edu.hm.renderer.ArrayRenderer")
        int[] array = {1, 2, 3,};
        @RenderMe
        private int foo;
        @RenderMe
        private Date date = new Date(123456789);

        public SomeClass(int foo) {
            this.foo = foo;
        }

        @RenderMe
        public int bla() {
            return 22;
        }
    }
}
