package cn.crabapples.redis;

import redis.embedded.RedisServer;

import java.util.Scanner;

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
