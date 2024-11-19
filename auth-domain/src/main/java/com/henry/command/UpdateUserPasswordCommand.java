package com.henry.command;

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
public class UpdateUserPasswordCommand implements IUserCommand {
    private String password;
    private UserStatus status;
    private Date updatedDate;
}
