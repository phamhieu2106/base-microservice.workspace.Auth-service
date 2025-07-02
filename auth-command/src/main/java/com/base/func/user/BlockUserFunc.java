package com.base.func.user;

import com.base.aggregate.BaseAggregate;
import com.base.aggregate.UserAggregate;
import com.base.command.BlockUserCommand;
import com.base.command.IUserCommand;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserStatus;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.repository.UserRepository;
import com.base.request.BlockUserRequest;
import com.base.util.NotificationUtils;
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

    public String exec(String id, BlockUserRequest request, String currentUsername) {

        Date now = new Date();

        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        BlockUserCommand command = new BlockUserCommand();
        command.setStatus(UserStatus.BLOCKED);
        command.setUpdatedDate(now);
        command.setLastModifiedBy(currentUsername);

        userAggregateRepository.update(userAggregate.getId(), command);

        //send notification for user
        notificationUtils.createNotificationBlockUser(userAggregate.getUsername());

        return userAggregate.getId();
    }
}
