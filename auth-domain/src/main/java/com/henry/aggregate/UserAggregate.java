package com.henry.aggregate;

import com.henry.Event;
import com.henry.base.DomainAggregate;
import com.henry.i_command.IUserCommand;
import com.henry.repository.UserRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class UserAggregate extends DomainAggregate<Event, IUserCommand> {
    private String fullName;
    private Date dob;
    private String phoneNumber;
    private String username;
    private String password;
    private Integer status;

    @Override
    public Class<?> loadRepositoryClazz() {
        return UserRepository.class;
    }
}
