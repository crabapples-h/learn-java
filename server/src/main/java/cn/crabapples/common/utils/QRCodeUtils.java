package cn.crabapples.common.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;

/**
 * TODO 二维码工具类
 *
 * @author Mr.He
 * 2019/11/16 14:36
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class QRCodeUtils {
    // 生成二维码的模板，使用草料二维码(无美化)
    public static final String QRCODE_TEMPLATE = "https://api.cl2wm.cn/api/qrcode/code?text=%s";
    // 生成二维码的模板，使用草料二维码(模板美化)
    public static final String QRCODE_TEMPLATE_BEAUTIFUL = "https://api.cl2wm.cn/api/qrcode/code?text=%s&mhid=5kLFC1rvkskhMHYpKtFUO6s";
    private static final Logger logger = LoggerFactory.getLogger(QRCodeUtils.class);

    public static void main(String[] args) {
        String text = "Hello, World!"; // 二维码内容
        printQrcode(20, 20, "Hello, World!");
    }

    /**
     * 打印二维码到控制台
     *
     * @param width   二维码宽度
     * @param height  二维码高度
     * @param content 二维码内容
     */
    public static void printQrcode(int width, int height, String content) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            Path tempDir = FileSystems.getDefault().getPath(".");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", tempDir.resolve("qrcode.png"));
            // 打印二维码到控制台
            System.out.println(bitMatrix);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 在线生成二维码
     *
     * @param content 二维码内容
     * @return 访问地址
     */
    public static String createOnlineQrcode(String content) {
        return String.format(QRCODE_TEMPLATE, content);
    }

    /**
     * @param outputStream 文件输出流
     * @param content      二维码携带信息
     * @param qrCodeSize   二维码图片大小
     * @param imageFormat  二维码的格式
     * @return 操作状态
     * @throws WriterException
     * @throws IOException
     */
    public static boolean createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat) throws WriterException, IOException {
        logger.info("开始生成二维码,内容[{}]", content);
        //设置二维码纠错级别ＭＡＰ
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i - 100, j - 100, 1, 1);
                }
            }
        }
        boolean status = ImageIO.write(image, imageFormat, outputStream);
        logger.info("二维码生成结束,状态[{}]", status);
        return status;
    }

    /**
     * 识别二维码并输出携带的信息
     *
     * @param inputStream 输入流
     * @return
     * @throws IOException
     * @throws FormatException
     * @throws ChecksumException
     * @throws NotFoundException
     */
    public static String readQrCode(InputStream inputStream) throws IOException, FormatException, ChecksumException, NotFoundException {
        logger.info("开始识别二维码");
        //从输入流中获取字符串信息
        BufferedImage image = ImageIO.read(inputStream);
        //将图像转换为二进制位图源
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap);
            logger.info("识别二维码结束:[{}]", result.getText());
            return result.getText();
        } catch (ReaderException e) {
            logger.error("识别二维码时出现异常:[]", e);
            throw e;
        }
    }
}
