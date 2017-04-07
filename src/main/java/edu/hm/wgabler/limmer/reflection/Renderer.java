package edu.hm.wgabler.limmer.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Renderer class to render objects annotated with @RenderMe.
 *
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @author Andrea Limmer, limmer@hm.edu
 * @since 27/03/2017
 */
public class Renderer {

    /**
     * Object to be rendered.
     */
    private final Object object;

    /**
     * Create new Renderer for given object.
     *
     * @param object Given object, not null.
     */
    public Renderer(Object object) {
        this.object = Objects.requireNonNull(object, "Object must not be null.");
    }

    /**
     * Generate a human-readable representation of the Renderer's object.
     *
     * @return String representation.
     */
    public String render() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Instance of ").append(getObject().getClass().getCanonicalName()).append('\n');
        Stream.of(getObject().getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RenderMe.class))
                .sorted(Comparator.comparing(Field::getName))
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
                .sorted(Comparator.comparing(Method::getName))
                .forEach(m -> {
                    try {
                        appendMethodInfo(m, builder);
                    } catch (InvocationTargetException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                });
        return builder.toString();
    }

    /**
     * Append human-readable representation of a given Field to the StringBuilder.
     *
     * @param field   Field to be rendered.
     * @param builder StringBuilder to be appended to.
     * @throws IllegalAccessException If an IllegalAccessException occurs.
     * @throws ClassNotFoundException If an ClassNotFoundException occurs.
     * @throws InstantiationException If an InstantiationException occurs.
     */
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

    /**
     * Append human-readable representation of a given Method with return-value to the StringBuilder.
     *
     * @param method  Method to be rendered.
     * @param builder StringBuilder to be appended to.
     * @throws InvocationTargetException If an InvocationTargetException occurs.
     * @throws IllegalAccessException    If an IllegalAccessException occurs.
     * @throws ClassNotFoundException    If an ClassNotFoundException occurs.
     * @throws InstantiationException    If an InstantiationException occurs.
     */
    private void appendMethodInfo(Method method, StringBuilder builder) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        method.setAccessible(true);
        Object result = method.invoke(getObject());
        builder.append("Method ").append(method.getName())
                .append(" (Type ").append(method.getReturnType().getCanonicalName()).append("): ");

        final String with = method.getAnnotation(RenderMe.class).with();
        if (with.equals("edu.hm.wgabler.limmer.reflection.ArrayRenderer")) {
            ArrayRenderer renderer = (ArrayRenderer) Class.forName(with).newInstance();
            final String rendered = renderer.render((int[]) result);
            builder.append(rendered).append("\n");
        } else {
            builder.append(result).append("\n");
        }
    }

    /**
     * Get object.
     *
     * @return Object.
     */
    private Object getObject() {
        return object;
    }
}
