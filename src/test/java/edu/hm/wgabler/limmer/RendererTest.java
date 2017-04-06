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
//@RunWith(Parameterized.class)
public class RendererTest {
	
	private SomeClass toRender;
	private Renderer renderer;
	
	
	/*@Parameters
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
                 }
           });
    }
    
    @Parameter // first value of parameters
    public Object objectToRender;

    @Parameter(1)
    public String expectedRendering;*/

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

        @RenderMe
        public char returnSomeChar() {
        	return 'X';
        }
        
        @RenderMe
        public char returnSomeCharWithParam(char input) {
        	return input;
        }
        
        @RenderMe
        public void noReturnValue() {
        	return;
        }
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
	
	/*@Test 
	public void testRendering() throws Exception {
		renderer = new Renderer(objectToRender);
		assertEquals(expectedRendering, renderer.render());
	}*/
	
	@Test 
	public void testMethodRendering() {
		renderer = new Renderer(new SomeClass(3));
		assertEquals(
				"Instance of edu.hm.wgabler.limmer.RendererTest.SomeClass\n" +
         				"foo (Type int): 3\n" +
         				"date (Type java.util.Date): Fri Jan 02 11:17:36 CET 1970\n", 
				renderer.render());
		
	}
	

}
