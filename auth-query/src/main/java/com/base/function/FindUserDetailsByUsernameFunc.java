package com.base.function;

import com.base.aggregate.UserAggregate;
import com.base.func.BaseFunc;
import com.base.repository.UserRepository;
import com.base.response.UserDetailResponse;
import com.base.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserDetailsByUsernameFunc extends BaseFunc {

    private final UserRepository userRepository;

    public UserDetailResponse exec(String username) {
        UserAggregate userAggregate = userRepository.findByUsername(username).orElse(null);

        if (ObjectUtils.isEmpty(userAggregate)) {
            return null;
        }

        return MappingUtils.mapObject(userAggregate, UserDetailResponse.class);
    }
}
