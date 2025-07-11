package com.base.func.user;

import com.base.aggregate.BaseAggregate;
import com.base.aggregate.UserAggregate;
import com.base.command.IUserCommand;
import com.base.command.UpdateUserPasswordCommand;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserStatus;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.repository.UserRepository;
import com.base.request.UpdateUserPasswordRequest;
import com.base.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UpdateUserPasswordFunc extends BaseFunc {
    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String exec(UpdateUserPasswordRequest request, String currentUsername) {
        UserAggregate userAggregate = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        return execInLockWithTransaction(UserAggregate.class + userAggregate.getId(),
                () -> runInternal(userAggregate, request, currentUsername));
    }

    public String runInternal(UserAggregate userAggregate, UpdateUserPasswordRequest request, String currentUsername) {
        Date now = new Date();

        if (ObjectUtils.notEqual(request.getPassword(), request.getConfirmPassword())) {
            throw new ServiceException(AuthErrorCode.PASSWORD_NOT_MATCH);
        }

        request.setConfirmPassword(passwordEncoder.encode(request.getConfirmPassword()));

        UpdateUserPasswordCommand command = MappingUtils.mapObject(request, UpdateUserPasswordCommand.class);
        command.setStatus(UserStatus.ACTIVE);
        command.setPassword(passwordEncoder.encode(request.getPassword()));
        command.setUpdatedDate(now);
        command.setLastModifiedBy(currentUsername);

        userAggregateRepository.update(userAggregate.getId(), command);
        return userAggregate.getId();
    }
}
