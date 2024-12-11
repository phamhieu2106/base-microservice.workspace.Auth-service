package com.henry.func.user;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.constant.HistoryType;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.command.IUserCommand;
import com.henry.command.UpdateUserCommand;
import com.henry.constant.AuthErrorCode;
import com.henry.repository.UserRepository;
import com.henry.request.user.UpdateUserRequest;
import com.henry.util.HistoryUtils;
import com.henry.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class UpdateUserFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;
    private final HistoryUtils historyUtils;

    public String exec(String id, UpdateUserRequest request) {
        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        UpdateUserCommand command = MappingUtils.mapObject(request, UpdateUserCommand.class);

        userAggregateRepository.update(userAggregate.getId(), command);
        historyUtils.saveHistory(userAggregate.getId(), userAggregate.getUsername(), UserAggregate.class, HistoryType.UPDATE, null);
        return userAggregate.getId();
    }
}
