package com.base.resource;

import com.base.domain.response.WrapResponse;
import com.base.func.user.*;
import com.base.request.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @PostMapping("/create/{confirmToken}")
    public CompletableFuture<WrapResponse<String>> create(@Valid @RequestBody CreateUserRequest request, @PathVariable String confirmToken) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request, confirmToken)), executorService);
    }

    @PostMapping("/confirm-active/{confirmToken}")
    public CompletableFuture<WrapResponse<SignUpRequest>> confirm(@PathVariable String confirmToken) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(ConfirmActiveUserFunc.class).exec(confirmToken)), executorService);
    }


    @PostMapping("/update/{id}")
    public CompletableFuture<WrapResponse<String>> update(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request,
                                                          Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserFunc.class).exec(id, request, principal.getName())), executorService);
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
