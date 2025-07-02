package com.base.command;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CreateUserCommand implements IUserCommand {
    private String username;
    private String phoneNumber;
    private String email;
    private String fullName;
    private String password;
    private Date dateOfBirth;
    private Integer status;
    private List<String> authorities;
    private String createdBy;
    private Date createdDate;
}
