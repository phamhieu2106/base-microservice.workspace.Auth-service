package com.base.request;

import com.base.constant.AuthErrorCode;
import com.base.domain.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest extends BaseRequest {
    @NotBlank(message = AuthErrorCode.USER_USERNAME_EMPTY)
    private String username;
    @NotBlank(message = AuthErrorCode.USER_EMAIL_EMPTY)
    private String email;
    private String phoneNumber;
    private String fullName;
    private Date dateOfBirth;

    private String token;
}
