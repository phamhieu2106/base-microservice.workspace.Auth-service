package com.base.event;

import com.base.event.base.BaseUserEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserEvent extends BaseUserEvent {
    private String username;
    private String phoneNumber;
    private String email;
    private String fullName;
    private Date dateOfBirth;
    private String password;
    private Integer status;
    private List<String> authorities;
    private String actionUser;
    private String createdBy;
    private Date createdDate;
}
