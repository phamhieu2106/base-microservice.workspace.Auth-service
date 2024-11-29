package com.henry.function;

import com.henry.aggregate.UserAggregate;
import com.henry.base.func.BaseFunc;
import com.henry.constant.AuthActionType;
import com.henry.repository.UserRepository;
import com.henry.repository.UserViewRepository;
import com.henry.util.MappingUtils;
import com.henry.util.StringUtils;
import com.henry.view.UserView;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncUserViewFunc extends BaseFunc {

    private final UserRepository userRepository;
    private final UserViewRepository userViewRepository;

    public void exec(String entityId, String version, AuthActionType actionType) {
        try {
            UserAggregate userAggregate = userRepository.findByVersionAndId(version, entityId).orElse(null);
            if (ObjectUtils.isNotEmpty(userAggregate)) {
                UserView userView = MappingUtils.mapObject(userAggregate, UserView.class);
                userView.setFullNameSort(StringUtils.convertSortStringView(userAggregate.getFullName()));
                userView.setCreatedDate(userAggregate.getCreatedDate());
                userViewRepository.save(userView);
                logger.info("Sync UserView Successfully with id: {} version: {} with action: {}", entityId, version, actionType);
            }
        } catch (Exception e) {
            logger.error("Sync UserView Failed with id: {} version: {} with action: {}", entityId, version, actionType);
            logger.error("Error: {}", e.getMessage());
        }
    }
}
