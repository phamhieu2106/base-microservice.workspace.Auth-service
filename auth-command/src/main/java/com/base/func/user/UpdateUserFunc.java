package com.base.func.user;

import com.base.aggregate.UserAggregate;
import com.base.base.aggregate.BaseAggregate;
import com.base.base.constant.HistoryType;
import com.base.base.exception.ServiceException;
import com.base.base.func.BaseFunc;
import com.base.command.IUserCommand;
import com.base.command.UpdateUserCommand;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserRole;
import com.base.entity.UserHistoryEntity;
import com.base.repository.UserHistoryRepository;
import com.base.repository.UserRepository;
import com.base.request.UpdateUserRequest;
import com.base.util.MappingUtils;
import com.base.util.PermissionUtils;
import com.base.utils.HistoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional
public class UpdateUserFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;
    private final HistoryUtils<UserHistoryEntity, UserHistoryRepository> historyUtils;

    public String exec(String id, UpdateUserRequest request, String currentUsername) {
        PermissionUtils.hasRole(UserRole.ALL_ROLE);

        Date now = new Date();
        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        UpdateUserCommand command = MappingUtils.mapObject(request, UpdateUserCommand.class);
        command.setLastModifiedBy(currentUsername);
        command.setUpdatedDate(now);

        historyUtils.saveHistoryWithChanges(userAggregate.getId(), userAggregate.getUsername(), UpdateUserRequest.class,
                UserAggregate.class, HistoryType.UPDATE, null, request, userAggregate, now);

        userAggregateRepository.update(userAggregate.getId(), command);
        return userAggregate.getId();
    }
}
