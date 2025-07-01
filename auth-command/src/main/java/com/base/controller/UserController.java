package com.base.controller;

import com.base.base.controller.BaseController;
import com.base.base.domain.response.WrapResponse;
import com.base.func.test_func.CreateUsersFunc;
import com.base.func.user.*;
import com.base.request.BlockUserRequest;
import com.base.request.CreateUserRequest;
import com.base.request.UpdateUserPasswordRequest;
import com.base.request.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${henry.prefix.api}/${spring.application.name}/users")
public class UserController extends BaseController {

    @PostMapping("/create")
    public CompletableFuture<WrapResponse<String>> create(@Valid @RequestBody CreateUserRequest request, Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request, principal.getName())), executorService);
    }
    
    @PostMapping("/create-users")
    public CompletableFuture<WrapResponse<String>> createUsers(@Valid @RequestBody List<CreateUserRequest> request,
                                                               Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUsersFunc.class).exec(request, principal.getName())), executorService);
    }

    @PostMapping("/update/{id}")
    public CompletableFuture<WrapResponse<String>> update(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request,
                                                          Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserFunc.class).exec(id, request, principal.getName())), executorService);
    }

    @PostMapping("/confirm-active/{id}")
    public CompletableFuture<WrapResponse<String>> confirm(@PathVariable String id, Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(ConfirmActiveUserFunc.class).exec(id, principal.getName())), executorService);
    }

    @PostMapping("/block/{id}")
    public CompletableFuture<WrapResponse<String>> block(@PathVariable String id, @Valid @RequestBody BlockUserRequest request,
                                                         Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(BlockUserFunc.class).exec(id, request, principal.getName())), executorService);
    }

    @PostMapping("/update-user-password")
    public CompletableFuture<WrapResponse<String>> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest request,
                                                                      Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserPasswordFunc.class).exec(request, principal.getName())), executorService);
    }
}
