package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.base")
public class AuthQueryApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AuthQueryApplication.class);
        application.run(args);
    }
}