package cn.crabapples.common.minio;

import cn.crabapples.common.base.ApplicationException;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
@Slf4j
public class MinioUtils {
    private final MinioClient minioClient;
    private final MinioConfigure configure;

    public MinioUtils(MinioClient minioClient, MinioConfigure configure) {
        this.minioClient = minioClient;
        this.configure = configure;
    }

    public String upload(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(configure.getBucketName())
                    .object(multipartFile.getName())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(args);
            inputStream.close();
            String object = objectWriteResponse.object();
            System.err.println(object);
//            List<Bucket> buckets = minioClient.listBuckets();
//            System.err.println(buckets);
        } catch (ErrorResponseException | InternalException | IOException | ServerException | XmlParserException |
                 InsufficientDataException | InvalidResponseException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ApplicationException("文件上传失败");
        } catch (InvalidKeyException e) {
            log.info("minioKey错误");
            throw new ApplicationException("文件上传失败:-3");
        }

        return "";
    }

    public String upload(MultipartFile multipartFile, String filePath) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            UploadObjectArgs args = UploadObjectArgs.builder()
                    .bucket(configure.getBucketName())
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
