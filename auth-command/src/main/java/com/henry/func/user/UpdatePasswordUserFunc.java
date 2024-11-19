package com.henry.func.user;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.command.IUserCommand;
import com.henry.command.UpdateUserPasswordCommand;
import com.henry.constant.AuthErrorCode;
import com.henry.constant.UserStatus;
import com.henry.repository.UserRepository;
import com.henry.request.user.UpdateUserPasswordRequest;
import com.henry.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional
public class UpdatePasswordUserFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;

    public String exec(String username, UpdateUserPasswordRequest request) {

        UserAggregate userAggregate = userRepository.findByUsername(username).orElseThrow(() ->
                new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        UpdateUserPasswordCommand command = MappingUtils.mapObject(request, UpdateUserPasswordCommand.class);
        command.setStatus(UserStatus.ACTIVE);
        command.setUpdatedDate(new Date());

        logger.info(">>>>> UpdatePasswordUserFunc update password Successfully for id: {}", userAggregate.getId());

        return userAggregateRepository.update(userAggregate.getId(), command).getId();
    }
}
