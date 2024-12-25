package com.henry.func.user;

import com.henry.aggregate.UserAggregate;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.constant.AuthErrorCode;
import com.henry.constant.UserStatus;
import com.henry.repository.UserRepository;
import com.henry.util.NotificationUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmActiveUserFunc extends BaseFunc {
    private final UserRepository userRepository;
    private final NotificationUtils notificationUtils;

    public String exec(String id) {
        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        if (ObjectUtils.notEqual(UserStatus.INACTIVE, userAggregate.getStatus())) {
            throw new ServiceException(AuthErrorCode.USER_ACTIVE_NOT_AVAILABLE);
        }

        return userAggregate.getId();
    }
}
