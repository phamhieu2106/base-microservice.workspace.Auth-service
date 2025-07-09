package com.base.request;

import com.base.constant.AuthErrorCode;
import com.base.domain.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotUserPasswordRequest extends BaseRequest {
    @NotBlank(message = AuthErrorCode.USER_EMAIL_EMPTY)
    private String email;
}
