package com.henry.api;

import com.henry.base.domain.response.WrapResponse;
import com.henry.request.user.CreateUserRequest;
import com.henry.request.user.UpdateUserPasswordRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "auth-command", path = "internal/api/auth-command", url = "${henry.auth-command.api}")
public interface InternalAuthCommandApi {
    //User
    @PostMapping("/users/create")
    WrapResponse<String> create(@Valid @RequestBody CreateUserRequest request);

    @PostMapping("/users/update-password/{username}")
    WrapResponse<String> updatePassword(@PathVariable String username, @Valid @RequestBody UpdateUserPasswordRequest request);
}
