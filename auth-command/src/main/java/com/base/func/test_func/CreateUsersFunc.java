package com.base.func.test_func;

import com.base.aggregate.BaseAggregate;
import com.base.aggregate.UserAggregate;
import com.base.command.CreateUserCommand;
import com.base.command.IUserCommand;
import com.base.constant.UserStatus;
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
public class CreateUsersFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;

    public String exec(List<CreateUserRequest> requests, String currentUsername) {
        Date now = new Date();
        requests.forEach(request -> {
            CreateUserCommand command = MappingUtils.mapObject(request, CreateUserCommand.class);
            command.setStatus(UserStatus.INACTIVE);
            command.setCreatedBy(currentUsername);
            command.setCreatedDate(now);
            UserAggregate userAggregate = userAggregateRepository.save(command);
        });
        return ">>>>>> Stored Successfully With: " + requests.size() + " requests";
    }
}
