package com.henry.func.user;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.constant.HistoryType;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.command.IUserCommand;
import com.henry.command.UpdateUserCommand;
import com.henry.constant.AuthErrorCode;
import com.henry.entity.UserHistoryEntity;
import com.henry.repository.UserHistoryRepository;
import com.henry.repository.UserRepository;
import com.henry.request.UpdateUserRequest;
import com.henry.util.MappingUtils;
import com.henry.utils.HistoryUtils;
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
