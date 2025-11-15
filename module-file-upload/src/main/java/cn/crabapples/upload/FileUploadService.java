package cn.crabapples.upload;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

@MapperScan("cn.crabapples.*.dao.mybatis.mapper")
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cn.crabapples"})
@Slf4j
public class FileUploadService {

    public static void main(String[] args) {
        log.info("文件启动服务中...");
        SpringApplication.run(FileUploadService.class, args);
    }

}
