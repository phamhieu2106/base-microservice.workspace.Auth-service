package com.henry.controller;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.base.func.QueryHistoryFunc;
import com.henry.base.request.QueryHistoryRequest;
import com.henry.base.response.HistoryResponse;
import com.henry.function.FindUserByIdFunc;
import com.henry.function.QueryUserFunc;
import com.henry.request.user.QueryUserRequest;
import com.henry.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${prefix.api}/${spring.application.name}/users")
public class UserController extends BaseController {

    @GetMapping("/find-by-id/{id}")
    public CompletableFuture<WrapResponse<UserResponse>> findById(@PathVariable String id) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(FindUserByIdFunc.class).exec(id)));
    }

    @PostMapping("/search")
    public CompletableFuture<WrapResponse<Page<UserResponse>>> search(@Valid @RequestBody QueryUserRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(QueryUserFunc.class).exec(request)));
    }

    @PostMapping("/search-histories")
    public CompletableFuture<WrapResponse<Page<HistoryResponse>>> searchHistories(@Valid @RequestBody QueryHistoryRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(QueryHistoryFunc.class).exec(request)));
    }
}
