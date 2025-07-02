package com.base.func.user;

import com.base.CacheUtils;
import com.base.aggregate.BaseAggregate;
import com.base.aggregate.UserAggregate;
import com.base.command.CreateUserCommand;
import com.base.command.IUserCommand;
import com.base.common.CommonConstant;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserRole;
import com.base.constant.UserStatus;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.repository.UserRepository;
import com.base.request.CreateUserRequest;
import com.base.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CreateUserFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final CacheUtils cacheUtils;
    private final PasswordEncoder passwordEncoder;

    public String exec(CreateUserRequest request, String confirmToken) {
        if (!cacheUtils.exists(confirmToken)) {
            throw new ServiceException(AuthErrorCode.CACHE_USER_EXPIRE);
        }

        if (ObjectUtils.notEqual(request.getPassword(), request.getConfirmPassword())) {
            throw new ServiceException(AuthErrorCode.PASSWORD_NOT_MATCH);
        }

        return execWithTransaction(() -> runInternal(request, confirmToken));
    }

    private String runInternal(CreateUserRequest request, String confirmToken) {
        Date currentDate = new Date();

        CreateUserCommand command = MappingUtils.mapObject(request, CreateUserCommand.class);
        command.setStatus(UserStatus.ACTIVE);
        command.setAuthorities(List.of(UserRole.USER));
        command.setCreatedDate(currentDate);
        command.setPassword(getPasswordEncode(request.getPassword()));

        UserAggregate userAggregate = userAggregateRepository.save(command);

        handleCacheUser(userAggregate.getUsername(), confirmToken);

        return userAggregate.getId();
    }

    private String getPasswordEncode(String password) {
        return passwordEncoder.encode(password);
    }

    private void handleCacheUser(String username, String confirmToken) {
        cacheUtils.addToSet(CommonConstant.AuthCacheKey.ACTIVE_USERNAME, username);
        cacheUtils.removeKey(confirmToken);
    }
}
