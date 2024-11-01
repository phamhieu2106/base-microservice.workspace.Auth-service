package com.henry.command;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserCommand implements IUserCommand {
    private String phoneNumber;
    private String email;
    private String fullName;
    private Date dateOfBirth;
    private String actionUser;
}
