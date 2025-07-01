package com.base.config;

import com.base.aggregate.BaseAggregate;
import com.base.aggregate.UserAggregate;
import com.base.command.IUserCommand;
import com.base.repository.EventRepository;
import com.base.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregate(UserRepository userRepository,
                                                                                    EventRepository eventRepository) {
        return new BaseAggregate<>(UserAggregate.class, userRepository, eventRepository);
    }
}
