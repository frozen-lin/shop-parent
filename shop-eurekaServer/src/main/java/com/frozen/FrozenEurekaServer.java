package com.frozen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <program> shopparent </program>
 * <description> Eurkea服务器启动类 </description>
 *
 * @author : lw
 * @date : 2020-03-25 14:38
 **/
@EnableEurekaServer
@SpringBootApplication
public class FrozenEurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(FrozenEurekaServer.class, args);
    }
}
