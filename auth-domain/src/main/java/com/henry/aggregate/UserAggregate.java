package com.henry.aggregate;

import com.henry.base.aggregate.DomainAggregate;
import com.henry.command.*;
import com.henry.constant.CustomJDBCType;
import com.henry.event.*;
import com.henry.repository.UserRepository;
import com.henry.util.MappingUtils;
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
public class UserAggregate extends DomainAggregate<UserAggregate, IUserCommand> {
    private String fullName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private Integer status;
    @Column(columnDefinition = CustomJDBCType.JSON)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Integer> authorities;

    @Override
    public Class<?> loadRepositoryClazz() {
        return UserRepository.class;
    }

    @Override
    public EventEntity processCommand(IUserCommand iUserCommand) {
        if (iUserCommand instanceof CreateUserCommand) {
            CreateUserCommand command = MappingUtils.mapObject(iUserCommand, CreateUserCommand.class);
            CreateUserEvent event = MappingUtils.mapObject(command, CreateUserEvent.class);
            MappingUtils.mapObject(event, this);
            return EventEntity.mapEventEntity(this.getId(), command, CreateUserEvent.class.getSimpleName(), command.getActionUser());

        } else if (iUserCommand instanceof UpdateUserCommand) {
            UpdateUserCommand command = MappingUtils.mapObject(iUserCommand, UpdateUserCommand.class);
            UpdateUserEvent event = MappingUtils.mapObject(command, UpdateUserEvent.class);
            MappingUtils.mapObject(event, this);
            return EventEntity.mapEventEntity(this.getId(), command, UpdateUserEvent.class.getSimpleName(), command.getActionUser());

        } else if (iUserCommand instanceof UpdateUserPasswordCommand) {
            UpdateUserPasswordCommand command = MappingUtils.mapObject(iUserCommand, UpdateUserPasswordCommand.class);
            UpdateUserPasswordEvent event = MappingUtils.mapObject(command, UpdateUserPasswordEvent.class);
            MappingUtils.mapObject(event, this);
            return EventEntity.mapEventEntity(this.getId(), command, UpdateUserPasswordEvent.class.getSimpleName(), null);

        } else if (iUserCommand instanceof BlockUserCommand) {
            BlockUserCommand command = MappingUtils.mapObject(iUserCommand, BlockUserCommand.class);
            BlockUserEvent event = MappingUtils.mapObject(command, BlockUserEvent.class);
            MappingUtils.mapObject(event, this);
            return EventEntity.mapEventEntity(this.getId(), command, BlockUserEvent.class.getSimpleName(), null);

        }
        return null;
    }
}
