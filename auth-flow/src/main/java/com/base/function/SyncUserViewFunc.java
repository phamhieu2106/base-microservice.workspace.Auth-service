package com.base.function;

import com.base.aggregate.UserAggregate;
import com.base.base.func.BaseFunc;
import com.base.constant.AuthActionType;
import com.base.repository.UserRepository;
import com.base.repository.UserViewRepository;
import com.base.util.MappingUtils;
import com.base.util.StringUtils;
import com.base.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SyncUserViewFunc extends BaseFunc {

    private final UserRepository userRepository;
    private final UserViewRepository userViewRepository;

    public void exec(String entityId, String version, AuthActionType actionType) {
        try {
            Optional<UserAggregate> userAggregateOpt = userRepository.findByIdAndVersion(entityId, version);
            userAggregateOpt.ifPresentOrElse(userAggregate -> {
                UserView userView = MappingUtils.mapObject(userAggregate, UserView.class);
                userView.setFullNameSort(StringUtils.convertSortStringView(userAggregate.getFullName()));
                userView.setCreatedDate(userAggregate.getCreatedDate());
                userViewRepository.save(userView);
                logger.info("Sync UserView Successfully with id: {} version: {} with action: {}", entityId, version, actionType.toString());
            }, () -> logger.error("Sync UserView with id: {} not found", entityId));
        } catch (Exception e) {
            logger.error("Sync UserView Failed with id: {} version: {} with action: {}", entityId, version, actionType.toString());
            logger.error("Error: {}", e.getMessage());
        }
    }
}
