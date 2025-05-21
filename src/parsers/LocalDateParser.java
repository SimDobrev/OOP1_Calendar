package parsers;

import interfaces.Parser;

import java.time.LocalDate;

/**
 * The {@code LocalDateParser} class converts a given string value to an object of
 * type {@code LocalDate}.
 */
public class LocalDateParser implements Parser<LocalDate> {
    /**
     * Converts the string value to a {@code LocalDate} object.
     * <p>
     *     Checks whether the given string is in the correct format. If yes, the method
     *     converts the value to an object of type {@code LocalDate}, otherwise, it
     *     prints an appropriate error message.
     * </p>
     * @param value The value to be converted.
     * @return The converted value as a {@code LocalDate} object.
     */
    @Override
    public LocalDate parse(String value) {
        if (value.length() != 10 || value.charAt(4) != '-' || value.charAt(7) != '-') {
            System.out.println("Error: Wrong date format.");
            System.out.println("Required date format: yyyy-MM-dd");
            return null;
        }

        LocalDate date;
        try {
            int year = Integer.parseInt(value.substring(0, 4));
            int month = Integer.parseInt(value.substring(5, 7));
            int day = Integer.parseInt(value.substring(8, 10));

            if (month == 2 && day > (year % 4 == 0 ? 29 : 28)) {
                System.out.println("February of " + year +
                        " has only " + (year % 4 == 0 ? 29 : 28) + " days!");
                return null;
            }

            final int[] monthsWith30Days = { 4, 6, 9, 11 };
            for (int entry : monthsWith30Days)
                if (month == entry && day == 31) {
                    System.out.println("This month has only 30 days!");
                    return null;
                }

            date = LocalDate.parse(value);
            if (date.isBefore(LocalDate.now())) {
                System.out.println("Dates cannot be earlier than the current date!");
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("Error: Wrong date format.");
            System.out.println("Required date format: yyyy-MM-dd");
            return null;
        }
        return date;
    }
}
