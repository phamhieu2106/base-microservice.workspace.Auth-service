package com.base.func.user;

import com.base.aggregate.UserAggregate;
import com.base.base.aggregate.BaseAggregate;
import com.base.base.constant.HistoryType;
import com.base.base.exception.ServiceException;
import com.base.base.func.BaseFunc;
import com.base.command.BlockUserCommand;
import com.base.command.IUserCommand;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserRole;
import com.base.constant.UserStatus;
import com.base.entity.UserHistoryEntity;
import com.base.repository.UserHistoryRepository;
import com.base.repository.UserRepository;
import com.base.request.BlockUserRequest;
import com.base.util.NotificationUtils;
import com.base.util.PermissionUtils;
import com.base.utils.HistoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional
public class BlockUserFunc extends BaseFunc {
    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;
    private final NotificationUtils notificationUtils;
    private final HistoryUtils<UserHistoryEntity, UserHistoryRepository> historyUtils;

    public String exec(String id, BlockUserRequest request, String currentUsername) {
        PermissionUtils.hasRole(UserRole.ADMIN);

        Date now = new Date();

        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        BlockUserCommand command = new BlockUserCommand();
        command.setStatus(UserStatus.BLOCKED);
        command.setUpdatedDate(now);
        command.setLastModifiedBy(currentUsername);

        userAggregateRepository.update(userAggregate.getId(), command);

        historyUtils.saveHistory(userAggregate.getId(), userAggregate.getUsername(), UserAggregate.class,
                HistoryType.BLOCK, request.getContent(), now);
        //send notification for user
        notificationUtils.createNotificationBlockUser(userAggregate.getUsername());

        return userAggregate.getId();
    }
}
