package com.henry.controller.internal;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.func.user.CreateUserFunc;
import com.henry.request.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/${prefix.api}/${spring.application.name}/users")
public class InternalUserController extends BaseController {

    @PostMapping("/create")
    public WrapResponse<String> create(@Valid @RequestBody CreateUserRequest request) {
        return WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request));
    }

}
