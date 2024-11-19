package com.henry.event;

import com.henry.constant.UserStatus;
import com.henry.event.base.BaseUserEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserPasswordEvent extends BaseUserEvent {
    private UserStatus status;
    private String password;
    private Date updatedDate;
}
