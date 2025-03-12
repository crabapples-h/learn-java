package cn.crabapples.common.minio;

import cn.crabapples.common.base.ApplicationException;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    public String upload(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(multipartFile.getOriginalFilename())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(args);
            inputStream.close();
            return bucketName;
        } catch (ErrorResponseException | InternalException | IOException | ServerException | XmlParserException |
                 InsufficientDataException | InvalidResponseException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ApplicationException("文件上传失败");
        } catch (InvalidKeyException e) {
            log.info("minioKey错误");
            throw new ApplicationException("文件上传失败:-3");
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
        } catch (ErrorResponseException | InternalException | IOException | ServerException | XmlParserException |
                 InsufficientDataException | InvalidResponseException | NoSuchAlgorithmException e) {
            throw new ApplicationException("文件上传失败");
        } catch (InvalidKeyException e) {
            log.info("minioKey错误");
            throw new ApplicationException("文件上传失败:-3");
        }

        return "";
    }

}
