package com.henry.controller.internal;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.function.ExitUserByUsernameFunc;
import com.henry.function.FindUserDetailsByUsernameFunc;
import com.henry.response.UserDetailResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/${prefix.api}/${spring.application.name}")
public class InternalUserController extends BaseController {

    @GetMapping("/users/exit-by-username/{username}")
    public WrapResponse<Boolean> exitByUsername(@PathVariable String username) {
        return WrapResponse.ok(applicationContext.getBean(ExitUserByUsernameFunc.class).exec(username));
    }

    @GetMapping("/users/get-user-detail/{username}")
    public WrapResponse<UserDetailResponse> getUserDetailsByUsername(@PathVariable String username) {
        return WrapResponse.ok(applicationContext.getBean(FindUserDetailsByUsernameFunc.class).exec(username));
    }
}
