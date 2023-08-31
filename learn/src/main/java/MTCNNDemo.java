//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.tzolov.cv.mtcnn.FaceAnnotation;
//import net.tzolov.cv.mtcnn.MtcnnService;
//import net.tzolov.cv.mtcnn.MtcnnUtil;
//import org.datavec.image.loader.Java2DNativeImageLoader;
//import org.deeplearning4j.nn.graph.ComputationGraph;
//import org.deeplearning4j.util.ModelSerializer;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.springframework.core.io.DefaultResourceLoader;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class MTCNNDemo {
//    public static void main(String[] args) {
//        // 加载 MTCNN 模型
//        ComputationGraph mtcnnModel;
//        try {
//            mtcnnModel = ModelSerializer.restoreComputationGraph(new File("path/to/mtcnn_model.zip"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        // 加载图像并进行预处理
//        BufferedImage image;
//        try {
//            image = loadImage("path/to/your/image.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        // 进行图像预处理和人脸检测
//        Java2DNativeImageLoader imageLoader = new Java2DNativeImageLoader();
//        INDArray imageArray = null;
//        try {
//            imageArray = imageLoader.asMatrix(image);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        // 图像预处理（例如缩放、归一化等，根据模型要求）
//        // ...
//
//        // 进行人脸检测
//        INDArray[] predictions = mtcnnModel.output(imageArray);
//
//        // 获取人脸框和关键点位置
//        INDArray boundingBoxes = predictions[0]; // 人脸框位置
//        INDArray keypoints = predictions[1]; // 关键点位置
//
//        // 处理检测结果，获取人脸框和关键点的坐标信息
//        // ...
//
//        // 输出检测结果
//        // ...
//
//    }
//
//    private static BufferedImage loadImage(String imagePath) throws IOException {
//        // 加载图像
//        File imageFile = new File(imagePath);
//        return javax.imageio.ImageIO.read(imageFile);
//    }
//}
