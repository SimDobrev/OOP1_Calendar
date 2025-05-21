package interfaces;

/**
 * An interface to be implemented by classes, purposed to convert a string value to
 * an object of the desired type.
 * @param <T> The desired object type
 */
public interface Parser<T> {
    /**
     * Gets the string value and converts it to the desired object type.
     * @param value the value to be converted
     * @return an object of the converted value
     */
    T parse(String value);
}
