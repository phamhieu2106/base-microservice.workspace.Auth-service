package com.base.resource.internal;

import com.base.domain.response.WrapResponse;
import com.base.func.user.CreateUserFunc;
import com.base.request.CreateUserRequest;
import com.base.resource.BaseController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class InternalUserController extends BaseController {

    @PostMapping("/users/create")
    public WrapResponse<String> create(@Valid @RequestBody CreateUserRequest request, @RequestParam String currentUsername) {
        return WrapResponse.ok(applicationContext.getBean(CreateUserFunc.class).exec(request, currentUsername));
    }

}
