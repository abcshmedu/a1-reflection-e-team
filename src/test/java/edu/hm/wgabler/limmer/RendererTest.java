/*
 * Wolfgang Gabler
 *
 * Software: Mac OS X 10.12, Oracle Java 1.8.0_111 SE
 * System: Intel Core i7-4850HQ, 16 GByte RAM
 */
package edu.hm.wgabler.limmer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import edu.hm.wgabler.limmer.reflection.RenderMe;
import edu.hm.wgabler.limmer.reflection.Renderer;

/**
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @since 27/03/2017
 */
@RunWith(Parameterized.class)
public class RendererTest {
	
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                 { 
                	 new SomeClass(22),
                	 "Instance of edu.hm.wgabler.limmer.RendererTest.SomeClass\n" +
             				"foo (Type int): 22\n" +
             				"date (Type java.util.Date): Fri Jan 02 11:17:36 CET 1970\n"
                 }, 
                 { 
                	 new SomeIntClass(1),
                	 "Instance of edu.hm.wgabler.limmer.RendererTest.SomeIntClass\n" +
             				"foo (Type int): 1\n" +
             				"bar (Type double): 42.0\n"
                 },
                 {
                	 new SomeMethodClass('a'),
                	 "Instance of edu.hm.wgabler.limmer.RendererTest.SomeMethodClass\n" +
                			 "foo (Type char): a\n" +
                			 "Method returnSomeChar (Type char): X\n" +
                			 "Method returnSomeString (Type class java.lang.String): blahblahblah\n"
                 },
                 {
                	 new SomeArrayClass(new int[] {1, 2, 3}),
                	 "Instance of edu.hm.wgabler.limmer.RendererTest.SomeArrayClass\n" + 
                			 "intArray (Type int[]): [1, 2, 3, ]\n" + 
                			 "Method returnIntArray (Type int[]): [11, 22, 33, ]\n"
                 }
           });
    }
    
    @Parameter // first value of given parameters
    public Object objectToRender;

    @Parameter(1)
    public String expectedRendering;


	@Test 
	public void testRendering() throws Exception {
		Renderer renderer = new Renderer(objectToRender);
		assertEquals(expectedRendering, renderer.render());
	}
	
	
    
	static class SomeClass {

        /*@RenderMe(with = "edu.hm.renderer.ArrayRenderer")
        int[] array = {1, 2, 3,};*/
        @RenderMe
        private int foo;
        @RenderMe
        private Date date = new Date(123456789);
        
        private int doNotRender = 42;

        public SomeClass(int foo) {
            this.foo = foo;
        }
    }
	
	static class SomeMethodClass {

        @RenderMe
        private char foo;

        public SomeMethodClass(char foo) {
            this.foo = foo;
        }

        @RenderMe
        public char returnSomeChar() {
        	return 'X';
        }
        
        @RenderMe
        public String returnSomeString() {
        	return "blahblahblah";
        }
        
        public int doNotRenderMethod() {
        	return 0;
        }
        
        @RenderMe	// should not render
        public char returnSomeCharWithParam(char input) {
        	return input;
        }
        
        @RenderMe	// should not render
        public void noReturnValue() { }
    }
	
	static class SomeIntClass {

        @RenderMe
        private int foo;
        @RenderMe
        private double bar = 42.0;

        public SomeIntClass(int foo) {
            this.foo = foo;
        }
    }
	
	static class SomeArrayClass {
		
		@RenderMe(with="edu.hm.wgabler.limmer.reflection.ArrayRenderer")
		int[] intArray = {1, 2, 3};
		
		// do not render
		int[] doNotRender = {4, 5, 6};
				
		public SomeArrayClass(int[] array) {
			intArray = array;
		}
		
		@RenderMe(with="edu.hm.wgabler.limmer.reflection.ArrayRenderer")
		int[] returnIntArray() {
			return new int[] {11, 22, 33};
		}
		
		// do not render
		int[] doNotRenderIntArray() {
			return new int[] {44, 55, 66};
		}
	}
	
	
	/*@Test 
	public void testMethodRendering() {
		renderer = new Renderer(new SomeClass(3));
		assertEquals(
				"Instance of edu.hm.wgabler.limmer.RendererTest.SomeClass\n" +
         				"foo (Type int): 3\n" +
         				"date (Type java.util.Date): Fri Jan 02 11:17:36 CET 1970\n" +
         				"method ...", 
				renderer.render());
		
	}*/
	

}
