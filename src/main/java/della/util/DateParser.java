package della.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    public static LocalDateTime parseDateTime(String dateString, String datePattern) throws DateTimeParseException {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(datePattern);
        return LocalDateTime.parse(dateString, inputFormat);
    }

    public static String printDateTime(LocalDateTime dateTime) {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        return dateTime.format(outputFormat);
    }
}
