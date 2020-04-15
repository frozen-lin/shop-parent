package com.frozen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author LIN
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@ServletComponentScan
public class ShopPcWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopPcWebApplication.class, args);
    }
}
