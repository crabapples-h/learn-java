package cn.crabapples.upload.oss;

import cn.crabapples.s3.service.S3Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/21 0:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration"})
@ActiveProfiles("dev")
//@EnableConfigurationProperties
//@ImportAutoConfiguration(exclude = {
//        EndPointConfigure.class,
//        EndPointConfigure.class,
//        WebSocketServerAdvanced.class,
//        WebSocketServerSimple.class,
//        AuthHandshakeInterceptor.class
//})

public class S3ServiceTest {
    @Autowired
    S3Service s3Service;

    @Test
    public void test1() {
        List<Bucket> buckets = s3Service.listBuckets();
        System.err.println(buckets);
    }

    @Test
    public void test2() {
        s3Service.createBucket(null);
        s3Service.createBucket("crabapples-test");
    }

    @Test
    public void test3() {
        s3Service.uploadFile("1/2/3/test.jpg", "/Users/mshe/developer/1.jpg");
        s3Service.uploadFile("crabapples-test", "1/2/3/test.jpg", "/Users/mshe/developer/1.jpg");
    }

    @Test
    public void test4() throws IOException {
        File file = new File("/Users/mshe/developer/1.jpg");
        RequestBody requestBody = RequestBody.fromInputStream(new FileInputStream(file), file.length());
        s3Service.uploadFile("test1.jpg", requestBody);
    }

    @Test
    public void test5() {
        s3Service.downloadFile("test1.jpg", "/Users/mshe/developer/2.jpg");
    }

    @Test
    public void test6() throws IOException {
        ResponseInputStream<GetObjectResponse> responseInputStream = s3Service.downloadFileAsStream("test1.jpg");
//        byte[] bytes = responseInputStream.readAllBytes();
//        FileOutputStream outputStream = new FileOutputStream("/Users/mshe/developer/3.jpg");
//        outputStream.write(bytes);
//        outputStream.close();
        byte[] b = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/mshe/developer/4.jpg");
        for (int i = responseInputStream.read(b); i != -1; i = responseInputStream.read(b)) {
            fileOutputStream.write(b, 0, i);

        }
        fileOutputStream.close();
    }

    @Test
    public void test7() throws IOException {
        ResponseBytes<GetObjectResponse> responseBytes = s3Service.downloadFileAsByte("test1.jpg");
        try (InputStream inputStream = responseBytes.asInputStream()) {
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/mshe/developer/5.jpg");
            byte[] b = new byte[1024];
            for (int i = inputStream.read(b); i != -1; i = inputStream.read(b)) {
                fileOutputStream.write(b, 0, i);
            }
            fileOutputStream.close();
        }
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/mshe/developer/6.jpg");
        fileOutputStream.write(responseBytes.asByteArray());
        fileOutputStream.close();
    }

    @Test
    public void test8() {
        PresignedGetObjectRequest presignedGetObjectRequest = s3Service.createTempDownloadUrl("test1.jpg", 1L, ChronoUnit.MINUTES);
        URL url = presignedGetObjectRequest.url();
        System.err.println(url);
    }

    @Test
    public void test9() {
        PresignedPutObjectRequest tempUploadUrl = s3Service.createTempUploadUrl("test2.jpg", 5L, ChronoUnit.MINUTES);
        URL url = tempUploadUrl.url();
        System.err.println(url);
    }

    @Test
    public void test10() throws IOException {
        String uploadId = s3Service.createMultipartUploadId("etcd.tar");
        File file = new File("/Users/mshe/developer/etcd.tar");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] b = new byte[1024 * 1024 * 5];
            for (int i = fileInputStream.read(b); i != -1; i = fileInputStream.read(b)) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(b, 0, i);
                RequestBody requestBody = RequestBody.fromRemainingByteBuffer(byteBuffer);
                s3Service.multipartUploadParts("etcd.tar", uploadId, requestBody);
            }
            s3Service.completedMultipartUpload("etcd.tar", uploadId);
        }
    }
}
