//package cn.crabapples.common.minio;
//
//import cn.crabapples.common.base.ApplicationException;
//import cn.hutool.core.lang.Snowflake;
//import io.minio.*;
//import io.minio.http.Method;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import javax.validation.constraints.NotNull;
//import java.io.BufferedOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.concurrent.TimeUnit;
//
//@EnableConfigurationProperties(MinioUtils.class)
//@ConfigurationProperties(prefix = "spring.minio")
//@Getter
//@Setter
//@Component
//@Slf4j
//public class MinioUtils {
//    private String accessKey;
//    private String secretKey;
//    private String url;
//    private String bucketName;
//    private Integer expiryTime;
//    private MinioClient minioClient;
//
//
//    @Bean
//    public MinioClient getMinioClient() {
//        if (null == minioClient) {
//            this.minioClient = MinioClient.builder()
//                    .endpoint(url)
//                    .credentials(accessKey, secretKey)
//                    .build();
//        }
//        return minioClient;
//    }
//
//    private String genRandomFileName(String fileName) {
//        String[] split = fileName.split("\\.");
//        String suffix = split.length > 0 ? "." + split[split.length - 1] : "";
//        return new Snowflake(1, 1).nextIdStr() + suffix;
//    }
//
//    private String doUpload(InputStream inputStream, String fileName, String bucketName) {
//        log.debug("开始上传文件到Minio:[{}]", fileName);
//        try {
//            PutObjectArgs args = PutObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(fileName)
//                    .stream(inputStream, inputStream.available(), -1)
//                    .build();
//            minioClient.putObject(args);
//            inputStream.close();
//            String fullUrl = "/" + bucketName + "/" + fileName;
//            log.debug("文件上传完成url:[{}]", fullUrl);
//            return fullUrl;
//        } catch (Exception e) {
//            throw new ApplicationException("文件上传失败", e);
//        }
//    }
//
//    public String upload(String bucketName, InputStream inputStream, String fileName) {
//        bucketName = bucketName == null ? this.bucketName : bucketName;
//        return upload(bucketName, inputStream, fileName, null);
//    }
//
//    public String upload(String bucketName, InputStream inputStream, String fileName, String customFileName) {
//        bucketName = bucketName == null ? this.bucketName : bucketName;
//        customFileName = customFileName == null ? genRandomFileName(fileName) : customFileName;
//        return doUpload(inputStream, customFileName, bucketName);
//    }
//
//
//    public void download(String fileName, OutputStream outputStream) {
//        log.debug("开始从Minio下载文件:[{}]", fileName);
//        try (BufferedOutputStream stream = new BufferedOutputStream(outputStream)) {
//            GetObjectArgs args = GetObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(fileName).build();
//            GetObjectResponse object = minioClient.getObject(args);
//            byte[] data = new byte[1024];
//            for (int i = object.read(data); i != -1; i = object.read(data)) {
//                stream.write(data, 0, i);
//            }
//            stream.flush();
//            object.close();
//            log.debug("从Minio下载文件[{}]完成", fileName);
//        } catch (Exception e) {
//            throw new ApplicationException("文件下载失败", e);
//        }
//    }
//
//    public String share(String fileName) {
//        try {
//            log.debug("开始从Minio获取文件分享连接:[{}]", fileName);
//            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
//                    .object(fileName)
//                    .method(Method.GET)
//                    .expiry(expiryTime, TimeUnit.HOURS)
//                    .build();
//            String shareUrl = minioClient.getPresignedObjectUrl(args);
//            log.debug("从Minio获取文件分享连接:[{}]完成,分享地址为:[{}]", fileName, shareUrl);
//            return shareUrl;
//        } catch (Exception e) {
//            throw new ApplicationException("文件分享失败", e);
//        }
//    }
//
//    public void remove(String fileName) {
//        try {
//            log.debug("开始从Minio删除文件:[{}]", fileName);
//            RemoveObjectArgs args = RemoveObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(fileName).build();
//            minioClient.removeObject(args);
//            log.debug("从Minio删除文件:[{}]完成", fileName);
//        } catch (Exception e) {
//            throw new ApplicationException("文件分享失败", e);
//        }
//    }
//}
