package cn.crabapples.upload;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("cn.crabapples.*.dao.mybatis.mapper")
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cn.crabapples"})
@Slf4j
public class FileUploadService {
    public static void main(String[] args) {
        log.info("文件服务启动中...");
        SpringApplication.run(FileUploadService.class, args);
    }

}
