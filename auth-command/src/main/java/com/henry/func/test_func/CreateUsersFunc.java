package com.henry.func.test_func;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.constant.HistoryType;
import com.henry.base.func.BaseFunc;
import com.henry.command.CreateUserCommand;
import com.henry.command.IUserCommand;
import com.henry.constant.UserStatus;
import com.henry.entity.UserHistoryEntity;
import com.henry.repository.UserHistoryRepository;
import com.henry.repository.UserRepository;
import com.henry.request.CreateUserRequest;
import com.henry.util.MappingUtils;
import com.henry.utils.HistoryUtils;
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
