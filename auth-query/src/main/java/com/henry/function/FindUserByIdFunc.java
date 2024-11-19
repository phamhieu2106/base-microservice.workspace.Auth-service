package com.henry.function;

import com.henry.aggregate.UserAggregate;
import com.henry.base.exception.ServiceException;
import com.henry.base.func.BaseFunc;
import com.henry.constant.AuthErrorCode;
import com.henry.repository.UserRepository;
import com.henry.response.UserResponse;
import com.henry.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserByIdFunc extends BaseFunc {

    private final UserRepository userRepository;

    public UserResponse exec(String id) {
        UserAggregate userAggregate = userRepository.findById(id).orElseThrow(()
                -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

        return MappingUtils.mapObject(userAggregate, UserResponse.class);
    }
}
