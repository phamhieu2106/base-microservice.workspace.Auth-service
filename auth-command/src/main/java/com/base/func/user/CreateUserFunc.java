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

    public String exec(CreateUserRequest request) {
        if (cacheUtils.isSetMember(CommonConstant.AuthCacheKey.ACTIVE_USERNAME, request.getUsername())) {
            throw new ServiceException(AuthErrorCode.CACHE_USER_EXPIRE);
        }

        return execWithTransaction(() -> runInternal(request));
    }

    public String runInternal(CreateUserRequest request) {
        Date currentDate = new Date();

        CreateUserCommand command = MappingUtils.mapObject(request, CreateUserCommand.class);
        command.setStatus(UserStatus.INACTIVE);
        command.setAuthorities(List.of(UserRole.USER));
        command.setCreatedDate(currentDate);

        UserAggregate userAggregate = userAggregateRepository.save(command);

        handleCacheUser(userAggregate.getUsername(), request.getToken());

        return userAggregate.getId();
    }

    private void handleCacheUser(String username, String confirmToken) {
        cacheUtils.addToSet(CommonConstant.AuthCacheKey.ACTIVE_USERNAME, username);
        cacheUtils.removeKey(confirmToken);
    }
}
