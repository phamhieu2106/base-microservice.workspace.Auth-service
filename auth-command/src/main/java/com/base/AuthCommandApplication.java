package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.base")
public class AuthCommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthCommandApplication.class, args);
    }
}