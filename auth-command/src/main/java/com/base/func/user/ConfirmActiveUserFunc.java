package com.base.func.user;

import com.base.aggregate.UserAggregate;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserRole;
import com.base.constant.UserStatus;
import com.base.repository.UserRepository;
import com.base.util.NotificationUtils;
import com.base.util.PermissionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmActiveUserFunc extends BaseFunc {
    private final UserRepository userRepository;

    public String exec(String id, String currentUsername) {
        PermissionUtils.hasRole(UserRole.ALL_ROLE);

        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        if (ObjectUtils.notEqual(UserStatus.INACTIVE, userAggregate.getStatus())) {
            throw new ServiceException(AuthErrorCode.USER_ACTIVE_NOT_AVAILABLE);
        }

        return userAggregate.getId();
    }
}
