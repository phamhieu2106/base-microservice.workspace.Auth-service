package com.henry.func;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.exception.ServiceException;
import com.henry.base.service.func.BaseFunc;
import com.henry.command.CreateUserCommand;
import com.henry.command.IUserCommand;
import com.henry.constant.AuthErrorCode;
import com.henry.constant.UserStatus;
import com.henry.repository.UserRepository;
import com.henry.request.CreateUserRequest;
import com.henry.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class CreateUserFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;

    public String exec(CreateUserRequest request) {
        validateRequest(request);

        CreateUserCommand command = MappingUtils.mapObject(request, CreateUserCommand.class);
        command.setStatus(UserStatus.ACTIVE);
        return userAggregateRepository.save(command).getId();
    }

    private void validateRequest(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        }
    }
}
