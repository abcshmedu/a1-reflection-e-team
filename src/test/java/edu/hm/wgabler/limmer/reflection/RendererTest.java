/*
 * Andrea Limmer
 *
 * Software: Windows 10 Home, Oracle Java 1.8.0_60 SE
 * System: Intel Atom x7-Z8700, 4 GByte RAM
 */
package edu.hm.wgabler.limmer.reflection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Parameterized Tests for RenderMe Reflection.
 *
 * @author Andrea Limmer, limmer@hm.edu
 * @since 27/03/2017
 */
@RunWith(Parameterized.class)
public class RendererTest {

    /**
     * Create Parameters for the tests.
     *
     * @return new Object, expected rendered String
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        new SomeClass(2),
                        "Instance of edu.hm.wgabler.limmer.reflection.RendererTest.SomeClass\n"
                                + "date (Type java.util.Date): Fri Jan 02 11:17:36 CET 1970\n"
                                + "foo (Type int): 2\n"
                },
                {
                        new SomeIntClass(1),
                        "Instance of edu.hm.wgabler.limmer.reflection.RendererTest.SomeIntClass\n"
                                + "bar (Type double): 42.0\n"
                                + "foo (Type int): 1\n"
                                + "Method returnDouble (Type double): 88.8\n"
                                + "Method returnInt (Type int): 99\n"
                },
                {
                        new SomeMethodClass('a'),
                        "Instance of edu.hm.wgabler.limmer.reflection.RendererTest.SomeMethodClass\n"
                                + "foo (Type char): a\n"
                                + "Method returnSomeChar (Type char): X\n"
                                + "Method returnSomeString (Type java.lang.String): blahblahblah\n"
                },
                {
                        new SomeArrayClass(new int[]{1, 2, 0}),
                        "Instance of edu.hm.wgabler.limmer.reflection.RendererTest.SomeArrayClass\n"
                                + "intArray (Type int[]): [1, 2, 0, ]\n"
                                + "Method returnIntArray (Type int[]): [11, 22, 33, ]\n"
                }
        });
    }

    /**
     * First value of the provided parameters.
     * An Object which should be rendered.
     * Must be public because of initialization of parameter.
     */
    @Parameter
    public Object objectToRender;

    /**
     * Second parameter.
     * String that is expected to be rendered with the given object.
     */
    @Parameter(1)
    public String expectedRendering;

    /**
     * Parameterized Test which tests the RenderMe Reflection.
     *
     * @throws Exception exception
     */
    @Test
    public void testRendering() throws Exception {
        Renderer renderer = new Renderer(objectToRender);
        assertEquals(expectedRendering, renderer.render());
    }

    /**
     * Renderer Constructor should reject null-objects.
     */
    @Test(expected = NullPointerException.class)
    public void ctorRejectsNull() {
        new Renderer(null);
    }

    /**
     * SomeClass to test basic rendering.
     */
    static class SomeClass {

        @RenderMe
        private final Date date = new Date(123456789);
        private final int doNotRender = 42;
        @RenderMe
        private int foo;

        /**
         * Constructor for SomeClass.
         *
         * @param foo some int value
         */
        SomeClass(int foo) {
            this.foo = foo;
        }
    }

    /**
     * SomeMethodClass to test rendering of methods.
     */
    static class SomeMethodClass {

        @RenderMe
        private char foo;

        /**
         * Constructor.
         *
         * @param foo char value
         */
        SomeMethodClass(char foo) {
            this.foo = foo;
        }

        /**
         * Returns a char.
         *
         * @return char.
         */
        @RenderMe
        public char returnSomeChar() {
            return 'X';
        }

        /**
         * Returns a string.
         *
         * @return String
         */
        @RenderMe
        public String returnSomeString() {
            return "blahblahblah";
        }

        /**
         * Should not be rendered.
         *
         * @return 0
         */
        public int doNotRenderMethod() {
            return 0;
        }

        /**
         * Should not be rendered as method has parameter.
         *
         * @param input some char
         * @return given input
         */
        @RenderMe
        public char returnSomeCharWithParam(char input) {
            return input;
        }

        /**
         * Should not be rendered as return type is void.
         */
        @RenderMe
        public void noReturnValue() {
        }
    }

    /**
     * SomeIntClass to test rendering for int & double.
     */
    static class SomeIntClass {

        @RenderMe
        private final double bar = 42.0;
        @RenderMe
        private int foo;

        /**
         * Constructor.
         *
         * @param foo an int value
         */
        SomeIntClass(int foo) {
            this.foo = foo;
        }

        /**
         * Return an int value.
         *
         * @return int
         */
        @RenderMe
        private int returnInt() {
            final int returnVal = 99;
            return returnVal;
        }

        /**
         * Return a double value.
         *
         * @return double
         */
        @RenderMe
        private double returnDouble() {
            final double returnVal = 88.8;
            return returnVal;
        }

        /**
         * Should not be rendered as return type is void.
         */
        @RenderMe
        private void doNotRenderVoid() {
        }
    }

    /**
     * SomeArrayClass to test rendering for int-arrays.
     */
    static class SomeArrayClass {

        /**
         * Should not be rendered as there is no "@RenderMe".
         */
        private final int[] doNotRender = {4, 5, 6};
        /**
         * Render with specified ArrayRenderer.
         */
        @RenderMe(with = "edu.hm.wgabler.limmer.reflection.ArrayRenderer")
        private int[] intArray;

        /**
         * Constructor.
         *
         * @param array an int array
         */
        SomeArrayClass(int[] array) {
            intArray = array;
        }

        /**
         * Render with ArrayRenderer.
         *
         * @return int-array
         */
        @RenderMe(with = "edu.hm.wgabler.limmer.reflection.ArrayRenderer")
        int[] returnIntArray() {
            final int[] array = new int[]{11, 22, 33};
            return array;
        }

        /**
         * Do not render as there is no "@RenderMe".
         *
         * @return int-array
         */
        int[] doNotRenderIntArray() {
            final int[] array = new int[]{44, 55, 66};
            return array;
        }
    }
}
