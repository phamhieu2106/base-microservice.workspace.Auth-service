package com.henry.response;

import com.henry.constant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private String id;
    private String fullName;
    private String fullNameSort;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private String username;
    private UserStatus status;
}
