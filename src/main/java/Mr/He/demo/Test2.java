package Mr.He.demo;

/**
 * TODO
 *
 * @author Mr.He
 * @date 2019/9/27 21:05
 * e-mail wishforyou.xia@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class Test2 {
    public static void main(String[] args) {
        String line = "100 999";
        String [] lines = line.split(" ");
        int nums [] = new int [lines.length];
        for(int i = 0; i < lines.length; i++){
            nums[i] = Integer.parseInt(lines[i]);
        }


        boolean flag = false;
        for(int i  = nums[0]; i <= nums[1]; i++){
            int a = i / 100;
            int b = i % 100 / 10;
            int c = i % 100 % 10;
            if(a*a*a + b*b*b + c*c*c == i){
                flag = true;
                System.err.println(i);
            }
        }
        if(!flag){
            System.err.println("no");
        }
    }
}
