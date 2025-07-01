package com.base.command;

import lombok.*;

import java.util.Date;
import java.util.List;

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
    private List<String> authorities;
    private Date updatedDate;
    private String lastModifiedBy;
}
