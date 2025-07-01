package com.base.command;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlockUserCommand implements IUserCommand {
    private Integer status;
    private Date updatedDate;
    private String lastModifiedBy;

}
