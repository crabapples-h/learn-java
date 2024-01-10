import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String a = "aacc";
        String b = "ccac";
        System.err.println(isAnagram(a, b));
    }

    public static boolean isAnagram(String a, String b) {
        int[] aArray = a.chars().sorted().toArray();
        int[] bArray = b.chars().sorted().toArray();
        return Arrays.equals(aArray, bArray);
    }
}
