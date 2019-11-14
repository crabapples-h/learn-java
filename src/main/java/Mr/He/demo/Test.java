package Mr.He.demo;

/**
 * TODO
 *
 * @author Mr.He
 * @date 2019/9/27 20:31
 * e-mail wishforyou.xia@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class Test {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int firstNumber = scanner.nextInt();
//        String secondLine = scanner.nextLine();

        int many = 5;
        String secondLine = "1,2,3,4,5";
        String [] numbers = secondLine.split(",");
        float [] nums = new float [numbers.length];
        for (int i = 0 ;i < numbers.length;i++) {
            nums[i] = Float.parseFloat(numbers[i]);
        }

        float startNum = 0;
        for(int i = 0; i < many; i++){
            if(i % 2 != 0){
                startNum = startNum - 1/nums[i];
            }else{
                startNum = startNum + 1/nums[i];
            }
        }
        System.err.printf("%.2f",startNum);
    }
}
