package com.henry.controller;

import com.henry.base.controller.BaseController;
import com.henry.base.service.response.WrapResponse;
import com.henry.func.CreateUserFunc;
import com.henry.func.UpdateUserFunc;
import com.henry.request.CreateUserRequest;
import com.henry.request.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${prefix.api}/${spring.application.name}/users")
public class UserController extends BaseController {

    @PostMapping("/create")
    public CompletableFuture<WrapResponse<String>> create(@Valid @RequestBody CreateUserRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request)), executorService);
    }

    @PostMapping("/update/{id}")
    public CompletableFuture<WrapResponse<String>> update(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserFunc.class).exec(id, request)), executorService);
    }

    @PostMapping("/confirm/{id}")
    public CompletableFuture<WrapResponse<String>> confirm(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok("OK"));
    }

    @PostMapping("/disable/{id}")
    public CompletableFuture<WrapResponse<String>> disable(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok("OK"));
    }
}
