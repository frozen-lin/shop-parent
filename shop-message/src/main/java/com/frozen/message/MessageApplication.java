package com.frozen.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <program> shopparent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-03-27 15:26
 **/
@SpringBootApplication
@EnableEurekaClient
public class MessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }
}
