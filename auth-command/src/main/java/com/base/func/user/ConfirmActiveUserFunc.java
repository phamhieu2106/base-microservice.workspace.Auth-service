package com.base.func.user;

import com.base.CacheUtils;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.func.BaseFunc;
import com.base.request.SignUpRequest;
import com.base.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmActiveUserFunc extends BaseFunc {

    private final CacheUtils cacheUtils;

    public SignUpRequest exec(String confirmToken) {
        if (!cacheUtils.exists(confirmToken)) {
            throw new ServiceException(AuthErrorCode.CACHE_USER_EXPIRE);
        }

        return MappingUtils.mapObject(cacheUtils.getValue(confirmToken), SignUpRequest.class);
    }
}
