/*
 * Wolfgang Gabler
 *
 * Software: Mac OS X 10.12, Oracle Java 1.8.0_111 SE
 * System: Intel Core i7-4850HQ, 16 GByte RAM
 */
package edu.hm.wgabler.limmer;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import edu.hm.wgabler.limmer.reflection.RenderMe;
import edu.hm.wgabler.limmer.reflection.Renderer;

/**
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @since 27/03/2017
 */
public class RendererTest {
	
	private SomeIntClass toRender;
	private Renderer renderer;

	static class SomeIntClass {

        @RenderMe
        private int foo;
        @RenderMe
        private int bar = 42;
        
        private int ignoreThisInt = 1;

        public SomeIntClass(int foo) {
            this.foo = foo;
        }
    }
	
	@Test 
	public void testIntRendering() throws Exception {
		toRender = new SomeIntClass(22);
		renderer = new Renderer(toRender);
		final String expected = "Instance of edu.hm.wgabler.limmer.RendererTest.SomeClass\n" +
				"foo (Type int): 22\n" +
				"bar (Type int): 42\n";
		assertEquals(expected, renderer.render());
	}

}
