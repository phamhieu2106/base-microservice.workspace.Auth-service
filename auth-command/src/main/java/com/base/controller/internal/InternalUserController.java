package com.base.controller.internal;

import com.base.base.controller.BaseController;
import com.base.base.domain.response.WrapResponse;
import com.base.func.user.CreateUserFunc;
import com.base.request.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/${henry.prefix.api}/${spring.application.name}/users")
public class InternalUserController extends BaseController {

    @PostMapping("/create")
    public WrapResponse<String> create(@Valid @RequestBody CreateUserRequest request, @RequestParam String currentUsername) {
        return WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request, currentUsername));
    }

}
