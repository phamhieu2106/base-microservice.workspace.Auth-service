package com.henry.controller;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.func.test_func.CreateUsersFunc;
import com.henry.func.user.BlockUserFunc;
import com.henry.func.user.ConfirmActiveUserFunc;
import com.henry.func.user.CreateUserFunc;
import com.henry.func.user.UpdateUserFunc;
import com.henry.request.user.BlockUserRequest;
import com.henry.request.user.CreateUserRequest;
import com.henry.request.user.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${prefix.api}/${spring.application.name}/users")
public class UserController extends BaseController {

    @PostMapping("/create")
    public CompletableFuture<WrapResponse<String>> create(@Valid @RequestBody CreateUserRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request)), executorService);
    }

    @PostMapping("/create-users")
    public CompletableFuture<WrapResponse<String>> createUsers(@Valid @RequestBody List<CreateUserRequest> request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUsersFunc.class).exec(request)), executorService);
    }

    @PostMapping("/update/{id}")
    public CompletableFuture<WrapResponse<String>> update(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserFunc.class).exec(id, request)), executorService);
    }

    @PostMapping("/confirm-active/{id}")
    public CompletableFuture<WrapResponse<String>> confirm(@PathVariable String id) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(ConfirmActiveUserFunc.class).exec(id)), executorService);
    }

    @PostMapping("/block/{id}")
    public CompletableFuture<WrapResponse<String>> block(@PathVariable String id, @Valid @RequestBody BlockUserRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(BlockUserFunc.class).exec(id, request)), executorService);
    }

}
