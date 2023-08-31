//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.tzolov.cv.mtcnn.FaceAnnotation;
//import net.tzolov.cv.mtcnn.MtcnnService;
//import net.tzolov.cv.mtcnn.MtcnnUtil;
//import org.springframework.core.io.DefaultResourceLoader;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class MTCNN {
//    public static void main(String[] args) throws IOException {
//        // 1. Create face detection service.
//        MtcnnService mtcnnService = new MtcnnService(30,
//                0.709, new double[]{0.6, 0.7, 0.7});
//
////        InputStream imageInputStream = new DefaultResourceLoader().getResource("classpath:/pivotal-ipo-nyse.jpg").getInputStream();
//
//        InputStream imageInputStream = new DefaultResourceLoader().getResource("D:/1.jpg").getInputStream();
//        // 2. Load the input image (you can use http:/, file:/ or classpath:/ URIs to resolve the input image
//        BufferedImage inputImage = ImageIO.read(imageInputStream);
//        // 3. Run face detection
//        FaceAnnotation[] faceAnnotations = mtcnnService.faceDetection(inputImage);
//        // 4. Augment the input image with the detected faces
//        BufferedImage annotatedImage = MtcnnUtil.drawFaceAnnotations(inputImage, faceAnnotations);
//        // 5. Store face-annotated image
//        ImageIO.write(annotatedImage, "jpg", new File(".d:/2.jpg"));
//        // 6. Print the face annotations as JSON
//        System.out.println("Face Annotations (JSON): " + new ObjectMapper().writeValueAsString(faceAnnotations));
//    }
//}
