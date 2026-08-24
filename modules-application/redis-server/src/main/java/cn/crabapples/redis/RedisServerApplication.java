package cn.crabapples.redis;

import redis.embedded.RedisServer;

import java.util.Scanner;

/**
 * TODO 内嵌redis服务
 * 需要特定平台的的可执行文件, mac上测试无法启动, 也不推荐使用这种方式启动
 *
 * @author Mr.He
 * 2021/4/10 15:33
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class RedisServerApplication {
    public static void main(String[] args) {
        System.out.println("start init inner redis server");
        RedisServer redisServer = RedisServer.builder()
                .port(6333)
                .setting("maxmemory 128M") //maxheap 128M
                .build();
        redisServer.stop();
        System.err.println(redisServer);
        redisServer.start();
        System.out.println("init inner redis server finish");
        System.out.println("input \"stop\" close this server");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.nextLine();
            if (s.equals("stop")) {
                redisServer.stop();
                break;
            }
        }
        System.out.println("close inner redis server");
    }
}
