package com.henry.config;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.repository.EventRepository;
import com.henry.command.IUserCommand;
import com.henry.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregate(
            UserRepository userRepository,
            EventRepository eventRepository) {
        return new BaseAggregate<>(userRepository, eventRepository, UserAggregate.class);
    }
}
