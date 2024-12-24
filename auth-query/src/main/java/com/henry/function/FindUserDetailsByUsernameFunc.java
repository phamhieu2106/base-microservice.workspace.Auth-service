package com.henry.function;

import com.henry.aggregate.UserAggregate;
import com.henry.base.func.BaseFunc;
import com.henry.repository.UserRepository;
import com.henry.response.UserDetailResponse;
import com.henry.util.MappingUtils;
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
