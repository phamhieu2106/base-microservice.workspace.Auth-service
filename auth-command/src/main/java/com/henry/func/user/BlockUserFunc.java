package com.henry.func.user;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.constant.HistoryType;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.command.BlockUserCommand;
import com.henry.command.IUserCommand;
import com.henry.constant.AuthErrorCode;
import com.henry.constant.UserStatus;
import com.henry.entity.UserHistoryEntity;
import com.henry.repository.UserHistoryRepository;
import com.henry.repository.UserRepository;
import com.henry.request.BlockUserRequest;
import com.henry.util.NotificationUtils;
import com.henry.utils.HistoryUtils;
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
