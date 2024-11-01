package com.henry.command;

import com.henry.constant.UserStatus;
import lombok.*;

import java.util.Date;

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
    private Date dateOfBirth;
    private UserStatus status;
    private String actionUser;
}
