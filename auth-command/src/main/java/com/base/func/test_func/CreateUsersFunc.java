package com.base.func.test_func;

import com.base.aggregate.UserAggregate;
import com.base.base.aggregate.BaseAggregate;
import com.base.base.constant.HistoryType;
import com.base.base.func.BaseFunc;
import com.base.command.CreateUserCommand;
import com.base.command.IUserCommand;
import com.base.constant.UserStatus;
import com.base.entity.UserHistoryEntity;
import com.base.repository.UserHistoryRepository;
import com.base.repository.UserRepository;
import com.base.request.CreateUserRequest;
import com.base.util.MappingUtils;
import com.base.utils.HistoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CreateUsersFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final HistoryUtils<UserHistoryEntity, UserHistoryRepository> historyUtils;

    public String exec(List<CreateUserRequest> requests, String currentUsername) {
        Date now = new Date();
        requests.forEach(request -> {
            CreateUserCommand command = MappingUtils.mapObject(request, CreateUserCommand.class);
            command.setStatus(UserStatus.INACTIVE);
            command.setCreatedBy(currentUsername);
            command.setCreatedDate(now);
            UserAggregate userAggregate = userAggregateRepository.save(command);
            historyUtils.saveHistory(userAggregate.getId(), userAggregate.getUsername(), UserAggregate.class, HistoryType.CREATE, null, now);
        });
        return ">>>>>> Stored Successfully With: " + requests.size() + " requests";
    }
}
