package com.base.func.user;

import com.base.aggregate.BaseAggregate;
import com.base.aggregate.UserAggregate;
import com.base.command.IUserCommand;
import com.base.command.UpdateUserCommand;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.repository.UserRepository;
import com.base.request.UpdateUserRequest;
import com.base.util.MappingUtils;
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

    public String exec(String id, UpdateUserRequest request, String currentUsername) {

        Date now = new Date();
        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        UpdateUserCommand command = MappingUtils.mapObject(request, UpdateUserCommand.class);
        command.setLastModifiedBy(currentUsername);
        command.setUpdatedDate(now);

        userAggregateRepository.update(userAggregate.getId(), command);
        return userAggregate.getId();
    }
}
