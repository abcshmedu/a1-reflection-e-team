package edu.hm.wgabler.limmer.reflection;

/**
 * An ArrayRenderer generates a String representation for a given int[] array.
 *
 * @author Wolfgang Gabler, wgabler@hm.edu
 * @since 30/03/2017
 */
public class ArrayRenderer {

    /**
     * Create human-readable String representation of an given int[] array.
     *
     * @param array Given int[] array.
     * @return String representation.
     */
    public String render(int[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i : array) {
            builder.append(i).append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

}
