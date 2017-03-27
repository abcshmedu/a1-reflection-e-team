/*
 * Wolfgang Gabler
 *
 * Software: Mac OS X 10.12, Oracle Java 1.8.0_111 SE
 * System: Intel Core i7-4850HQ, 16 GByte RAM
 */

package edu.hm.wgabler.limmer.reflection;

import java.util.stream.Stream;

/**
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @since 27/03/2017
 */
public class Renderer {

    private final Object toRender;

    public Renderer(Object toRender) {
        this.toRender = toRender;
    }

    public String render() {
        // TODO: parse annotations

//        final Collection<Method> renderedMethods =
        Stream.of(toRender.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(RenderMe.class))
//                .collect(Collectors.toList());
                .forEach(field -> {
                });


        return "";
    }
}
