package cn.crabapples.common.minio;

import cn.crabapples.common.base.ApplicationException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Configurable
@EnableConfigurationProperties(MinioUtils.class)
@ConfigurationProperties(prefix = "spring.minio")
@Getter
@Setter
@Component
@Slf4j
public class MinioUtils {
    private String accessKey;
    private String secretKey;
    private String url;
    private String bucketName;
    private Integer expiryTime;
    private MinioClient minioClient;


    @Bean
    public MinioClient getMinioClient() {
        if (null == minioClient) {
            this.minioClient = MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secretKey)
                    .build();
        }
        return minioClient;
    }

    public String upload(String fileName,InputStream inputStream) {
        try  {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(args);
            inputStream.close();
//            return "/" + bucketName + "/" + multipartFile.getOriginalFilename();
            return fileName;
        } catch (Exception e) {
            throw new ApplicationException("文件上传失败", e);
        }
    }

    public String upload(MultipartFile multipartFile, String filePath) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            UploadObjectArgs args = UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(multipartFile.getName())
                    .filename(filePath)
                    .build();
            minioClient.uploadObject(args);
            inputStream.close();
            List<Bucket> buckets = minioClient.listBuckets();
            System.err.println(buckets);
            return multipartFile.getOriginalFilename();
        } catch (Exception e) {
            throw new ApplicationException("文件上传失败", e);
        }
    }

    public static void main(String[] args) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String accessKey = "gQ6MwJZxCoPX4qoh2O80";
        String secretKey = "vLmlVPM1mG2cBjBu8mLXhNBpdGkRbgpBJBT9S5yz";
        String url = "http://172.16.8.1:9000";
        String bucketName = "crabapples";
        MinioClient client = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
        GetObjectArgs args1 = GetObjectArgs.builder()
                .bucket(bucketName)
                .object("test.txt").build();
        GetObjectResponse object = client.getObject(args1);
        byte[] data = new byte[1024];
        for (int i = object.read(data); i != -1; i = object.read(data)) {
            System.err.println(new String(data, 0, i));
        }
        object.close();
    }

    public void download(String fileName, OutputStream outputStream) {
        log.debug("开始从Minio下载文件:[{}]", fileName);
        try (BufferedOutputStream stream = new BufferedOutputStream(outputStream)) {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName).build();
            GetObjectResponse object = minioClient.getObject(args);
            byte[] data = new byte[1024];
            for (int i = object.read(data); i != -1; i = object.read(data)) {
                stream.write(data, 0, i);
            }
            stream.flush();
            object.close();
            log.debug("从Minio下载文件[{}]完成", fileName);
        } catch (Exception e) {
            throw new ApplicationException("文件下载失败", e);
        }
    }


    public String share(String fileName) {
        try {
            log.debug("开始从Minio获取文件分享连接:[{}]", fileName);
            String shareUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .method(Method.GET)
                    .expiry(expiryTime, TimeUnit.HOURS)
                    .build());
            log.debug("从Minio获取文件分享连接:[{}]完成,分享地址为:[{}]", fileName, shareUrl);
            return shareUrl;
        } catch (Exception e) {
            throw new ApplicationException("文件分享失败", e);
        }
    }
}
