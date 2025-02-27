package com.henry.controller;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.func.test_func.CreateUsersFunc;
import com.henry.func.user.*;
import com.henry.request.BlockUserRequest;
import com.henry.request.CreateUserRequest;
import com.henry.request.UpdateUserPasswordRequest;
import com.henry.request.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${henry.prefix.api}/${spring.application.name}/users")
public class UserController extends BaseController {

    @PostMapping("/create")
    @PreAuthorize("hasRole(T(com.henry.constant.UserRole).ADMIN)")
    public CompletableFuture<WrapResponse<String>> create(@Valid @RequestBody CreateUserRequest request, Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request, principal.getName())), executorService);
    }

    @PostMapping("/create-users")
    @PreAuthorize("hasRole(T(com.henry.constant.UserRole).ADMIN)")
    public CompletableFuture<WrapResponse<String>> createUsers(@Valid @RequestBody List<CreateUserRequest> request,
                                                               Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUsersFunc.class).exec(request, principal.getName())), executorService);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyRole(T(com.henry.constant.UserRole).ALL_ROLE)")
    public CompletableFuture<WrapResponse<String>> update(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request,
                                                          Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserFunc.class).exec(id, request, principal.getName())), executorService);
    }

    @PostMapping("/confirm-active/{id}")
    @PreAuthorize("hasAnyRole(T(com.henry.constant.UserRole).ALL_ROLE)")
    public CompletableFuture<WrapResponse<String>> confirm(@PathVariable String id, Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(ConfirmActiveUserFunc.class).exec(id, principal.getName())), executorService);
    }

    @PostMapping("/block/{id}")
    @PreAuthorize("hasRole(T(com.henry.constant.UserRole).ADMIN)")
    public CompletableFuture<WrapResponse<String>> block(@PathVariable String id, @Valid @RequestBody BlockUserRequest request,
                                                         Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(BlockUserFunc.class).exec(id, request, principal.getName())), executorService);
    }

    @PostMapping("/update-user-password")
    @PreAuthorize("hasAnyRole(T(com.henry.constant.UserRole).ALL_ROLE)")
    public CompletableFuture<WrapResponse<String>> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest request,
                                                                      Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserPasswordFunc.class).exec(request, principal.getName())), executorService);
    }
}
