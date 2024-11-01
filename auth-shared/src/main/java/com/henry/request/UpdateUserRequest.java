package com.henry.request;

import com.henry.request.base.BaseUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest extends BaseUserRequest {
    private String phoneNumber;
    private String email;
    private String fullName;
    private Date dateOfBirth;
}
