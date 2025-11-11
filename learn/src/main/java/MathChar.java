import java.math.BigDecimal;
import java.math.MathContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathChar {
    public static void main(String[] args) {
        String showsString = "1.3+1*3-5÷4";
        String REG_TEMPLATE = "[+\\-*÷]";
        Pattern pattern = Pattern.compile(REG_TEMPLATE);
        Matcher matcher = pattern.matcher(showsString);
        while (matcher.find()) {
            String group = matcher.group();
            System.err.println(group);
        }
        BigDecimal value = new BigDecimal("64");
        BigDecimal sqrt = value.sqrt(MathContext.UNLIMITED);
        System.err.println(sqrt);
    }
}
