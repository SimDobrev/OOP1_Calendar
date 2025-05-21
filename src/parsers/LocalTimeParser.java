package parsers;

import interfaces.Parser;

import java.time.LocalTime;

/**
 * The {@code LocalTimeParser} class converts a given string value to an object of
 * type {@code LocalTime}.
 */
public class LocalTimeParser implements Parser<LocalTime> {
    /**
     * Converts the string value to a {@code LocalTime} object.
     * <p>
     *     Checks whether the given string is in the correct format. If yes, the method
     *     converts the value to an object of type {@code LocalTime}, otherwise, it
     *     prints an appropriate error message.
     * </p>
     * @param value The value to be converted.
     * @return The converted value as a {@code LocalTime} object.
     */
    @Override
    public LocalTime parse(String value) {
        LocalTime time;
        try {
            boolean isCorrectFormat = true;
            if (value.length() == 8)
                isCorrectFormat = value.charAt(2) == ':' && value.charAt(5) == ':';
            else if (value.length() == 5)
                isCorrectFormat = value.charAt(2) == ':';

            if (!isCorrectFormat) {
                System.out.println("Error: Wrong time format.");
                System.out.println("Required time format: \"hh:mm:ss\" or \"hh:mm\"");
                return null;
            }

            int hour = Integer.parseInt(value.substring(0, 2));
            int minute = Integer.parseInt(value.substring(3, 5));
            if (hour < 0 || hour > 23) {
                System.out.println("Error: Hours can only be from 0 to 23!");
                return null;
            }
            if (minute < 0 || minute > 59) {
                System.out.println("Error: Minutes can only be from 0 to 59!");
                return null;
            }

            if (value.length() == 8) {
                int second = Integer.parseInt(value.substring(6, 8));
                if (second < 0 || second > 59) {
                    System.out.println("Error: Seconds can only be from 0 to 59!");
                    return null;
                }
            }

            time = LocalTime.parse(value);
        }
        catch (Exception e) {
            System.out.println("Error: Wrong time format.");
            System.out.println("Required time format: \"hh:mm:ss\" or \"hh:mm\"");
            return null;
        }
        return time;
    }
}
