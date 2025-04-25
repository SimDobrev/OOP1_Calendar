package parsers;

import interfaces.Parser;

import java.sql.Time;

public class TimeParser implements Parser<Time> {
    @Override
    public Time parse(String value) {
        Time time;
        try {
            if (value.length() != 10) {
                System.out.println("Error: Wrong time format\nRequired time format: \"hh:mm:ss\"\n");
                return null;
            }

            int hour = Integer.parseInt(value.substring(0, 2));
            int minute = Integer.parseInt(value.substring(3, 5));
            int second = Integer.parseInt(value.substring(6, 8));

            if (hour < 0 || hour > 23) {
                System.out.println("Error: Hours can only be from 0 to 23!\n");
                return null;
            }
            if ((minute < 0 || minute > 59) || (second < 0 || second > 59)) {
                System.out.println("Error: Minutes and seconds can only be from 0 to 59!\n");
                return null;
            }

            time = Time.valueOf(value);
        }
        catch (Exception e) {
            System.out.println("Error: Wrong time format\nRequired time format: \"hh:mm:ss\"\n");
            return null;
        }
        return time;
    }
}
