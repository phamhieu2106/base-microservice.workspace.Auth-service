package com.henry.func.test_func;

import com.henry.aggregate.UserAggregate;
import com.henry.base.aggregate.BaseAggregate;
import com.henry.base.func.BaseFunc;
import com.henry.command.CreateUserCommand;
import com.henry.command.IUserCommand;
import com.henry.constant.UserStatus;
import com.henry.repository.UserRepository;
import com.henry.request.CreateUserRequest;
import com.henry.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CreateUsersFunc extends BaseFunc {

    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;

    public String exec(List<CreateUserRequest> requests) {
        requests.forEach(request -> {
            CreateUserCommand command = MappingUtils.mapObject(request, CreateUserCommand.class);
            command.setStatus(UserStatus.INACTIVE);
            userAggregateRepository.save(command);
        });
        return ">>>>>> Stored Successfully With: " + requests.size() + " requests";
    }
}
