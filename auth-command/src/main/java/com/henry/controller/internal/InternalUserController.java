package com.henry.controller.internal;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.func.user.CreateUserFunc;
import com.henry.func.user.UpdatePasswordUserFunc;
import com.henry.request.user.CreateUserRequest;
import com.henry.request.user.UpdateUserPasswordRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/${prefix.api}/${spring.application.name}/users")
public class InternalUserController extends BaseController {

    @PostMapping("/create")
    public WrapResponse<String> create(@Valid @RequestBody CreateUserRequest request) {
        return WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request));
    }

    @PostMapping("/update-password/{username}")
    public WrapResponse<String> updatePassword(@PathVariable String username, @Valid @RequestBody UpdateUserPasswordRequest request) {
        return WrapResponse.ok(applicationContext.getBean(UpdatePasswordUserFunc.class).exec(username, request));
    }

}
