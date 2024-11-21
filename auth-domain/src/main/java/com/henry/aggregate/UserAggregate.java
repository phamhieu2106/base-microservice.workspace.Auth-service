package com.henry.aggregate;

import com.henry.base.aggregate.DomainAggregate;
import com.henry.command.CreateUserCommand;
import com.henry.command.IUserCommand;
import com.henry.command.UpdateUserCommand;
import com.henry.command.UpdateUserPasswordCommand;
import com.henry.constant.CustomJDBCType;
import com.henry.constant.UserRole;
import com.henry.constant.UserStatus;
import com.henry.converter.JDBCConverterToJson;
import com.henry.event.CreateUserEvent;
import com.henry.event.EventEntity;
import com.henry.event.UpdateUserEvent;
import com.henry.event.UpdateUserPasswordEvent;
import com.henry.repository.UserRepository;
import com.henry.util.MappingUtils;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private UserStatus status;
    @Column(columnDefinition = CustomJDBCType.JSON)
    @Convert(converter = JDBCConverterToJson.class)
    private List<UserRole> authorities;

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
        }
        return null;
    }
}
