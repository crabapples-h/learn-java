import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class OpencvMac {

    public static void main(String[] args) {
//        System.setProperty("java.library.path", "/usr/local/share/java/opencv4");
//        System.err.println(System.getProperty("java.library.path"));
        // 加载 OpenCV 库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 加载人脸检测器（使用已经训练好的级联分类器）
        CascadeClassifier faceDetector = new CascadeClassifier("/Users/mshe/developer/opencv-4.5.3/data/haarcascades/haarcascade_frontalface_default.xml");
        // 加载测试图像
        Mat image = Imgcodecs.imread("/Users/mshe/developer/image/1.JPEG");
        // 转换图像为灰度图
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        // 人脸检测
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(grayImage, faceDetections);
        System.err.println(faceDetections.toArray().length);
        // 在图像中绘制矩形框标记检测到的人脸
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
        }
        // 保存标记后的图像
        Imgcodecs.imwrite("/Users/mshe/developer/image/result.jpg", image);
        System.out.println("人脸检测完成，结果已保存。");
    }
}
