package com.base.func.user;

import com.base.aggregate.UserAggregate;
import com.base.base.aggregate.BaseAggregate;
import com.base.base.constant.HistoryType;
import com.base.base.exception.ServiceException;
import com.base.base.func.BaseFunc;
import com.base.command.CreateUserCommand;
import com.base.command.IUserCommand;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserRole;
import com.base.constant.UserStatus;
import com.base.entity.UserHistoryEntity;
import com.base.repository.UserHistoryRepository;
import com.base.repository.UserRepository;
import com.base.request.CreateUserRequest;
import com.base.util.MappingUtils;
import com.base.util.PermissionUtils;
import com.base.utils.HistoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CreateUserFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;
    private final HistoryUtils<UserHistoryEntity, UserHistoryRepository> historyUtils;

    public String exec(CreateUserRequest request, String currentUsername) {
        PermissionUtils.hasRole(UserRole.ADMIN);

        Date now = new Date();
        validateRequest(request);

        CreateUserCommand command = MappingUtils.mapObject(request, CreateUserCommand.class);
        command.setStatus(UserStatus.INACTIVE);
        command.setAuthorities(List.of(UserRole.USER));
        command.setCreatedBy(currentUsername);
        command.setCreatedDate(now);
        UserAggregate userAggregate = userAggregateRepository.save(command);
        historyUtils.saveHistory(userAggregate.getId(), userAggregate.getUsername(), UserAggregate.class, HistoryType.CREATE, null, now);
        return userAggregate.getId();
    }

    private void validateRequest(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new ServiceException(AuthErrorCode.USERNAME_EXIT);
        }
    }
}
