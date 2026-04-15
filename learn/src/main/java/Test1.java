import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test1 {
    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E");
        String format = LocalDate.now().format(dateTimeFormatter);
        System.err.println(format);

    }
}
