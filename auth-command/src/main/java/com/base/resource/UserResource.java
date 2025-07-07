package com.base.resource;

import com.base.domain.response.WrapResponse;
import com.base.func.user.BlockUserFunc;
import com.base.func.user.CreateUserFunc;
import com.base.func.user.UpdateUserFunc;
import com.base.func.user.UpdateUserPasswordFunc;
import com.base.request.BlockUserRequest;
import com.base.request.CreateUserRequest;
import com.base.request.UpdateUserPasswordRequest;
import com.base.request.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UserResource extends BaseResource {

    @PostMapping("/create")
    public CompletableFuture<WrapResponse<String>> create(@Valid @RequestBody CreateUserRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request)), executorService);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<WrapResponse<String>> update(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request,
                                                          Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserFunc.class).exec(id, request, principal.getName())), executorService);
    }

    @PutMapping("/block/{id}")
    public CompletableFuture<WrapResponse<String>> block(@PathVariable String id, @Valid @RequestBody BlockUserRequest request,
                                                         Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(BlockUserFunc.class).exec(id, request, principal.getName())), executorService);
    }

    @PutMapping("/update-user-password")
    public CompletableFuture<WrapResponse<String>> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest request,
                                                                      Principal principal) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(UpdateUserPasswordFunc.class).exec(request, principal.getName())), executorService);
    }
}
