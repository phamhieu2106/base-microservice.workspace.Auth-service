package com.base.command;

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
    private Integer status;
    private Date updatedDate;
    private String lastModifiedBy;

}
