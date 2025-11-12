package cn.crabapples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) throws IOException {
        log.info("启动nacos服务...");
        Process pwd = Runtime.getRuntime().exec("docker-compose -f ./docker-compose/nacos/docker-compose-nacos.yml up -d");
        InputStream inputStream = pwd.getInputStream();
        byte[] data = new byte[1024];
        for (int i = inputStream.read(data); i != -1; i = inputStream.read(data)) {
            System.out.print(new String(data, 0, i));
        }
//        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PreDestroy
    void destroy() throws IOException {
        log.info("关闭nacos服务");
        Process pwd = Runtime.getRuntime().exec("docker-compose -f ./docker-compose/nacos/docker-compose-nacos.yml down");
        InputStream inputStream = pwd.getInputStream();
        byte[] data = new byte[1024];
        for (int i = inputStream.read(data); i != -1; i = inputStream.read(data)) {
            System.out.print(new String(data, 0, i));
        }
    }
}
