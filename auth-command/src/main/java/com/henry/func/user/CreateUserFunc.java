package com.henry.func.user;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.constant.HistoryType;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.command.CreateUserCommand;
import com.henry.command.IUserCommand;
import com.henry.constant.AuthErrorCode;
import com.henry.constant.UserRole;
import com.henry.constant.UserStatus;
import com.henry.repository.UserRepository;
import com.henry.request.user.CreateUserRequest;
import com.henry.util.HistoryUtils;
import com.henry.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CreateUserFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;
    private final HistoryUtils historyUtils;

    public String exec(CreateUserRequest request) {
        validateRequest(request);

        CreateUserCommand command = MappingUtils.mapObject(request, CreateUserCommand.class);
        command.setStatus(UserStatus.INACTIVE);
        command.setAuthorities(List.of(UserRole.USER));
        UserAggregate userAggregate = userAggregateRepository.save(command);
        historyUtils.saveHistory(userAggregate.getId(), userAggregate.getUsername(), UserAggregate.class, HistoryType.CREATE, null);
        return userAggregate.getId();
    }

    private void validateRequest(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        }
    }
}
