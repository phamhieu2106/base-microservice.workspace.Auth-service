package com.henry.controller;

import com.henry.base.service.response.WrapResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${prefix.api}/${spring.application.name}/users")
public class UserController {

    @GetMapping("/hello")
    public CompletableFuture<WrapResponse<String>> hello() {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok("OK"));
    }
}
