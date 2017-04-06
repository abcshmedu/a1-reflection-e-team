/*
 * Wolfgang Gabler
 *
 * Software: Mac OS X 10.12, Oracle Java 1.8.0_111 SE
 * System: Intel Core i7-4850HQ, 16 GByte RAM
 */
package edu.hm.wgabler.limmer.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @author Andrea Limmer, limmer@hm.edu
 * @since 27/03/2017
 */
public class Renderer {

    private final Object object;

    public Renderer(Object object) {
        this.object = object;
    }

    public String render() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Instance of ").append(getObject().getClass().getCanonicalName()).append('\n');
        Stream.of(getObject().getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RenderMe.class))
                .forEach(f -> {
                    try {
                        appendFieldInfo(f, builder);
                    } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                });
        Stream.of(getObject().getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(RenderMe.class))
                .filter(m -> m.getParameterCount() == 0)
                .filter(m -> !m.getReturnType().equals(Void.TYPE))
                .forEach(m -> {
                    try {
                        appendMethodInfo(m, builder);
                    } catch (InvocationTargetException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                });
        return builder.toString();
    }

    private void appendFieldInfo(Field field, StringBuilder builder) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        field.setAccessible(true);
        builder.append(field.getName())
                .append(" (Type ").append(field.getType().getCanonicalName()).append("): ");

        final String with = field.getAnnotation(RenderMe.class).with();
        if (with.equals("edu.hm.wgabler.limmer.reflection.ArrayRenderer")) {
            ArrayRenderer renderer = (ArrayRenderer) Class.forName(with).newInstance();
            final String rendered = renderer.render((int[]) field.get(getObject()));
            builder.append(rendered).append("\n");
        } else {
            builder.append(field.get(getObject())).append("\n");
        }
    }

    private void appendMethodInfo(Method method, StringBuilder builder) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        method.setAccessible(true);
        Object result = method.invoke(getObject());
        builder.append("Method ").append(method.getName())
                .append(" (Type ").append(method.getReturnType()).append("): ");

        final String with = method.getAnnotation(RenderMe.class).with();
        if (with.equals("edu.hm.wgabler.limmer.reflection.ArrayRenderer")) {
            ArrayRenderer renderer = (ArrayRenderer) Class.forName(with).newInstance();
            final String rendered = renderer.render((int[]) result);
            builder.append(rendered).append("\n");
        } else {
            builder.append(result).append("\n");
        }
    }

    private Object getObject() {
        return object;
    }
}
