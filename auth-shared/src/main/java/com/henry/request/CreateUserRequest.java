package com.henry.request;

import com.henry.constant.AuthErrorCode;
import com.henry.request.base.BaseUserRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest extends BaseUserRequest {
    @NotBlank(message = AuthErrorCode.USER_USERNAME_EMPTY)
    private String username;
    private String phoneNumber;
    private String email;
    private String fullName;
    private Date dateOfBirth;
}
