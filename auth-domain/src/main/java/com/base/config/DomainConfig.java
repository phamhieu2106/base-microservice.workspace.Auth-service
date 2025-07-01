package com.base.config;

import com.base.aggregate.UserAggregate;
import com.base.base.aggregate.BaseAggregate;
import com.base.base.repository.EventRepository;
import com.base.command.IUserCommand;
import com.base.entity.UserHistoryEntity;
import com.base.repository.UserHistoryRepository;
import com.base.repository.UserRepository;
import com.base.utils.HistoryUtils;
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

    @Bean
    public HistoryUtils<UserHistoryEntity, UserHistoryRepository> userHistoryUtils(UserHistoryRepository userHistoryRepository) {
        return new HistoryUtils<>(userHistoryRepository, UserHistoryEntity.class);
    }
}
