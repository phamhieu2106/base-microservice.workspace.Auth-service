package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.base")
public class AuthFlowApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AuthFlowApplication.class);
        application.run(args);
    }
}