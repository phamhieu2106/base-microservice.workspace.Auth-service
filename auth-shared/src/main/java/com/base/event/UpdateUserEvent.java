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
public class UpdateUserEvent extends BaseUserEvent {
    private String phoneNumber;
    private String email;
    private String fullName;
    private Date dateOfBirth;
    private List<String> authorities;
    private Date updatedDate;
    private String lastModifiedBy;
}
