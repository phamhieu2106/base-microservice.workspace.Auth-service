package com.henry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.henry")
@EnableFeignClients
public class AuthCommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthCommandApplication.class, args);
    }
}