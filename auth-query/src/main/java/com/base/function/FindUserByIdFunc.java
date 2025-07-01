package com.base.function;

import com.base.aggregate.UserAggregate;
import com.base.base.exception.ServiceException;
import com.base.base.func.BaseFunc;
import com.base.constant.AuthErrorCode;
import com.base.repository.UserRepository;
import com.base.response.UserResponse;
import com.base.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserByIdFunc extends BaseFunc {

    private final UserRepository userRepository;

    public UserResponse exec(String id) {
//        PermissionUtils.hasPermission(UserRole.ADMIN);
        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        return MappingUtils.mapObject(userAggregate, UserResponse.class);
    }
}
