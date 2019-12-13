package Mr.He.demo;

import Mr.He.spring.common.utils.ImageUtils;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import sun.security.util.SecurityConstants;

import java.io.*;
import java.util.Random;

public class CreateQrCode {

    public static final char [] CHAR = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static void create(String string) throws Exception {
        File file = new File("/home/mrhe/applications/1.png");
        OutputStream outputStream = new FileOutputStream(file);
        InputStream inputStream = new FileInputStream(file);
        System.err.println(ImageUtils.createQrCode(outputStream,string,1500,"png"));
        System.err.println(ImageUtils.readQrCode(inputStream));
    }

    //当测试authTest时候，把genSecretTest生成的secret值赋值给它
    private static String secret="R2Q3S52RNXBTFTOM";

    public static void main(String[] args) throws Exception {// 生成密钥
//        secret = GoogleAuthenticator.generateSecretKey();
//        // 把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功
//        String qrcode = GoogleAuthenticator.getQRBarcode("2816661736@qq.com", secret);
//        create(qrcode);
        create("otpauth://totp/'456'?secret=AAAAAAAAAA");
//        System.out.println("qrcode:" + qrcode + ",key:" + secret);
    }
}



