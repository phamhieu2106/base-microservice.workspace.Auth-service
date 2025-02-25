package com.henry.controller.internal;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.func.user.CreateUserFunc;
import com.henry.request.CreateUserRequest;
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
