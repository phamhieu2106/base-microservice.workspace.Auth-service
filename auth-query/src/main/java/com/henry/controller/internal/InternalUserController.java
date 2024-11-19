package com.henry.controller.internal;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.function.ExitUserByUsernameFunc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/${prefix.api}/${spring.application.name}/users")
public class InternalUserController extends BaseController {

    @PostMapping("/exit-by-username/{username}")
    public WrapResponse<Boolean> exitByUsername(@PathVariable String username) {
        return WrapResponse.ok(applicationContext.getBean(ExitUserByUsernameFunc.class).exec(username));
    }
}
