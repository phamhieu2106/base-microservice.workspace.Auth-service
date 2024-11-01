package com.henry.aggregate;

import com.henry.base.aggregate.DomainAggregate;
import com.henry.command.CreateUserCommand;
import com.henry.command.IUserCommand;
import com.henry.command.UpdateUserCommand;
import com.henry.constant.UserStatus;
import com.henry.event.CreateUserEvent;
import com.henry.event.EventEntity;
import com.henry.event.UpdateUserEvent;
import com.henry.repository.UserRepository;
import com.henry.util.MappingUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "username_idx", columnList = "username")
})
public class UserAggregate extends DomainAggregate<UserAggregate, IUserCommand> {
    private String fullName;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Date dob;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private UserStatus status;

    @Override
    public Class<?> loadRepositoryClazz() {
        return UserRepository.class;
    }

    @Override
    public EventEntity processCommand(IUserCommand command) {
        if (command instanceof CreateUserCommand) {
            CreateUserCommand createUserCommand = MappingUtils.mapObject(command, CreateUserCommand.class);
            CreateUserEvent createUserEvent = MappingUtils.mapObject(createUserCommand, CreateUserEvent.class);
            MappingUtils.mapObject(createUserEvent, this);
            return EventEntity.mapEventEntity(this.getId(), createUserCommand, CreateUserEvent.class.getSimpleName(), createUserCommand.getActionUser());
        } else if (command instanceof UpdateUserCommand) {
            UpdateUserCommand updateUserCommand = MappingUtils.mapObject(command, UpdateUserCommand.class);
            UpdateUserEvent updateUserEvent = MappingUtils.mapObject(updateUserCommand, UpdateUserEvent.class);
            MappingUtils.mapObject(updateUserEvent, this);
            return EventEntity.mapEventEntity(this.getId(), updateUserCommand, UpdateUserEvent.class.getSimpleName(), updateUserCommand.getActionUser());
        }
        return null;
    }
}
