import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class OpencvWindows {
    private static String MODEL_PATH = "D:\\developer\\opencv-4.5.3\\opencv\\sources\\data\\haarcascades\\" +
            "haarcascade_frontalface_default.xml";

    public static void main(String[] args) {
        // 加载 OpenCV 库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        System.load("D:\\developer\\opencv-4.5.3\\opencv\\build\\java\\x64\\opencv_java453.dll");
        // 加载人脸检测器（使用已经训练好的级联分类器）
//        CascadeClassifier faceDetector = new CascadeClassifier("D:\\developer\\opencv-4.5.3\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_default.xml");
        CascadeClassifier faceDetector = new CascadeClassifier(MODEL_PATH);
        // 加载测试图像
        Mat image = Imgcodecs.imread("D:\\1.jpg");
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
        Imgcodecs.imwrite("D:\\2.jpg", image);
        System.out.println("人脸检测完成，结果已保存。");
    }
}
