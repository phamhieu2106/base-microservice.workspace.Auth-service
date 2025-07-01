package com.base.event;

import com.base.event.base.BaseUserEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlockUserEvent extends BaseUserEvent {
    private Integer status;
    private Date updatedDate;
}
