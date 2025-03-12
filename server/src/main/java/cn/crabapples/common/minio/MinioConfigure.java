//package cn.crabapples.common.minio;
//
//import io.minio.MinioClient;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Configurable
//@EnableConfigurationProperties(MinioConfigure.class)
//@ConfigurationProperties(prefix = "spring.minio")
//@Getter
//@Setter
//@Component
//public class MinioConfigure {
//    private String accessKey;
//    private String secretKey;
//    private String url;
//    private String bucketName;
//
//    @Bean
//    public MinioClient getMinioClient() {
//        return MinioClient.builder()
//                .endpoint(url)
//                .credentials(accessKey, secretKey)
//                .build();
//    }
//}
