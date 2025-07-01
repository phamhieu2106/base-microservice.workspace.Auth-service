package com.base.resource;

import com.base.domain.response.WrapResponse;
import com.base.function.FindUserByIdFunc;
import com.base.function.QueryUserFunc;
import com.base.function.QueryUserHistoryFunc;
import com.base.request.QueryHistoryRequest;
import com.base.request.QueryUserRequest;
import com.base.response.HistoryResponse;
import com.base.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${henry.prefix.api}/${spring.application.name}/users")
public class UserResource extends BaseController {

    @GetMapping("/find-by-id/{id}")
    public CompletableFuture<WrapResponse<UserResponse>> findById(@PathVariable String id) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(FindUserByIdFunc.class).exec(id)), executorService);
    }

    @PostMapping("/search")
    public CompletableFuture<WrapResponse<Page<UserResponse>>> search(@Valid @RequestBody QueryUserRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(QueryUserFunc.class).exec(request)), executorService);
    }

    @PostMapping("/search-histories")
    public CompletableFuture<WrapResponse<Page<HistoryResponse>>> searchHistories(@Valid @RequestBody QueryHistoryRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(applicationContext.getBean(QueryUserHistoryFunc.class).exec(request)), executorService);
    }
}
