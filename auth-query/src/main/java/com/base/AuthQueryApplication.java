package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.base")
public class AuthQueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthQueryApplication.class, args);
    }
}