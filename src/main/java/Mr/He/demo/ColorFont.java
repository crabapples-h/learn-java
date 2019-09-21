package Mr.He.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorFont {
    public static void main(String[] args) {
        String str = "\033[1;31m hello world \033[0m";

        String startRex = "\\033.{1}\\d{1,2};\\d{1,2}m";
        String endRex = "\\033.{1}\\d{1,2}m";

        Pattern startPattern = Pattern.compile(startRex);
        Pattern endPattern = Pattern.compile(endRex);

        Matcher startMatcher = startPattern.matcher(str);
        Matcher endMatcher = endPattern.matcher(str);

        System.out.println(startMatcher.matches());
        System.out.println(endMatcher.matches());

        System.out.println("\n");

//        str = str.replaceAll(startRex,"");
//        str = str.replaceAll(endRex,"");

        System.out.println(str);
    }
}
