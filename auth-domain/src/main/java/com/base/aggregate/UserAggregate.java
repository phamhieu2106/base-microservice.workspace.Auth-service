package com.base.aggregate;

import com.base.common.Event;
import com.base.util.EventUtils;
import com.base.command.*;
import com.base.constant.JDBCCustomType;
import com.base.event.BlockUserEvent;
import com.base.event.CreateUserEvent;
import com.base.event.UpdateUserEvent;
import com.base.event.UpdateUserPasswordEvent;
import com.base.util.MappingUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "id_version_idx", columnList = "id,version"),
        @Index(name = "username_idx", columnList = "username")
})
@SuppressWarnings("unused")
public class UserAggregate extends DomainAggregate<UserAggregate, IUserCommand> {
    private String fullName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private Integer status;
    @Column(columnDefinition = JDBCCustomType.JSON)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> authorities;

    public Event process(CreateUserCommand command) {
        CreateUserEvent event = MappingUtils.mapObject(command, CreateUserEvent.class);
        return EventUtils.getEvent(event);
    }

    public void apply(CreateUserEvent event) {
        MappingUtils.mapObject(event, this);
    }

    public Event process(UpdateUserCommand command) {
        UpdateUserEvent event = MappingUtils.mapObject(command, UpdateUserEvent.class);
        return EventUtils.getEvent(event);
    }

    public void apply(UpdateUserEvent event) {
        MappingUtils.mapObject(event, this);
    }

    public Event process(UpdateUserPasswordCommand command) {
        UpdateUserPasswordEvent event = MappingUtils.mapObject(command, UpdateUserPasswordEvent.class);
        return EventUtils.getEvent(event);
    }

    public void apply(UpdateUserPasswordEvent event) {
        MappingUtils.mapObject(event, this);
    }

    public Event process(BlockUserCommand command) {
        BlockUserEvent event = MappingUtils.mapObject(command, BlockUserEvent.class);
        return EventUtils.getEvent(event);
    }

    public void apply(BlockUserEvent event) {
        MappingUtils.mapObject(event, this);
    }
}
