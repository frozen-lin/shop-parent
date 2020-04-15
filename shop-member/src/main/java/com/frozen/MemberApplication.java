package com.frozen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <program> shopparent </program>
 * <description> 会员服务应用 </description>
 *
 * @author : lw
 * @date : 2020-03-25 14:50
 **/
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = "com.frozen.member.mapper")
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
