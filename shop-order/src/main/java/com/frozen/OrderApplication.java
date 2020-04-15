package com.frozen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <program> shop-parent </program>
 * <description> 订单Service </description>
 *
 * @author : lw
 * @date : 2020-04-03 11:55
 **/
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.frozen.order.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
