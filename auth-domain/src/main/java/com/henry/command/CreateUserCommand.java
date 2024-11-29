package com.henry.command;

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
    private Date dateOfBirth;
    private Integer status;
    private List<Integer> authorities;
    private String actionUser;
}
