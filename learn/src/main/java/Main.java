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
//    public static void main(String[] args) {
////        moveZeroes(new int[]{0, 0, 1});
////        moveZeroes(new int[]{0, 1, 0});
//        moveZeroes(new int[]{0, 1, 0, 3, 12});
//    }

    public static void moveZeroes(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                count++;
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == 0) {
                moveLeft(i, nums);
            }
        }
        for (int i = nums.length - count; i < nums.length; i++) {
            nums[i] = 0;
        }
        for (int num : nums) {
            System.err.println(num);
        }
    }

    public static void moveLeft(int index, int[] nums) {
        for (int i = index; i < nums.length; i++) {
            if (i < nums.length - 1) {
                nums[i] = nums[i + 1];
            }
        }
    }
}
