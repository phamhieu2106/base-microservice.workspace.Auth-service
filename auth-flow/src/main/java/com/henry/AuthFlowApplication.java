package com.henry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.henry")
public class AuthFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthFlowApplication.class, args);
    }
}