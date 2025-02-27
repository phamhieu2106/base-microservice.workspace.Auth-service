package com.henry.func.user;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.constant.HistoryType;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.command.IUserCommand;
import com.henry.command.UpdateUserPasswordCommand;
import com.henry.constant.AuthErrorCode;
import com.henry.constant.UserStatus;
import com.henry.entity.UserHistoryEntity;
import com.henry.repository.UserHistoryRepository;
import com.henry.repository.UserRepository;
import com.henry.request.UpdateUserPasswordRequest;
import com.henry.util.MappingUtils;
import com.henry.utils.HistoryUtils;
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
    private final HistoryUtils<UserHistoryEntity, UserHistoryRepository> historyUtils;
    private final PasswordEncoder passwordEncoder;

    public String exec(UpdateUserPasswordRequest request, String currentUsername) {
        Date now = new Date();

        UserAggregate userAggregate = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

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
        historyUtils.saveHistory(userAggregate.getId(), userAggregate.getUsername(), UserAggregate.class, HistoryType.UPDATE_PASSWORD, null, now);
        return userAggregate.getId();
    }
}
