package com.henry.controller;

import com.henry.base.exception.ServiceException;
import com.henry.base.service.response.WrapResponse;
import com.henry.constant.AuthErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${prefix.api}/${spring.application.name}/users")
public class UserController {

    @GetMapping("/hello")
    public CompletableFuture<WrapResponse<String>> hello() {
        throw new ServiceException(AuthErrorCode.TEST);
    }
}
