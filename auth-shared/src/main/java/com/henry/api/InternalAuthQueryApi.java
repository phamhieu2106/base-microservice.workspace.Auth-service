package com.henry.api;

import com.henry.base.domain.response.WrapResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-query", path = "internal/api/auth-query", url = "${henry.auth-query.api}")
public interface InternalAuthQueryApi {

    @PostMapping("/users/exit-by-username/{username}")
    WrapResponse<Boolean> exitByUsername(@PathVariable String username);
}
