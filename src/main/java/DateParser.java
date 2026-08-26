import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateParser {
    public static LocalDateTime parseDateTime(String dateString) throws DateTimeParseException {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, inputFormat);
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("DateTime cannot be before today");
        }
        return dateTime;
    }

    public static String printDateTime(LocalDateTime dateTime) {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        return dateTime.format(outputFormat);
    }
}
