package parsers;

import interfaces.Parser;

import java.time.LocalDate;
import java.sql.Date;

public class DateParser implements Parser<Date> {
    @Override
    public Date parse(String value) {
        if (value.length() != 10 || value.charAt(2) != '/' || value.charAt(5) != '/') {
            System.out.println("Date format: dd/MM/yyyy\n");
            return null;
        }

        Date date;
        try {
            int day = Integer.parseInt(value.substring(0, 2));
            int month = Integer.parseInt(value.substring(3, 5));
            int year = Integer.parseInt(value.substring(6, 10));

            if (month == 2 && day > (year % 4 == 0 ? 29 : 28)) {
                System.out.println("February of " + year +
                        " has only " + (year % 4 == 0 ? 29 : 28) + " days!\n");
                return null;
            }

            final int[] monthsWith30Days = { 4, 6, 9, 11 };
            for (int entry : monthsWith30Days)
                if (month == entry && day == 31) {
                    System.out.println("This month has only 30 days!\n");
                    return null;
                }

            date = Date.valueOf(value.substring(6, 10) + '-' + value.substring(3, 5) + '-' + value.substring(0, 2));
            if (date.compareTo(Date.valueOf(LocalDate.now())) < 0) {
                System.out.println("Dates cannot be earlier than the current date!\n");
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("Error: Wrong date format\nRequired time format: \"dd/MM/yyyy\"\n");
            return null;
        }
        return date;
    }
}
