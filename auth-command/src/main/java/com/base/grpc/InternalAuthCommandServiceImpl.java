package com.base.grpc;

import com.base.aggregate.BaseAggregate;
import com.base.aggregate.UserAggregate;
import com.base.command.IUserCommand;
import com.base.command.UpdateUserPasswordCommand;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserStatus;
import com.base.exception.ServiceException;
import com.base.func.user.CreateUserFunc;
import com.base.repository.UserRepository;
import com.base.request.CreateUserRequest;
import com.base.util.MappingUtils;
import com.base.utils.GrpcHandlerUtils;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;

@GrpcService
@Component
@RequiredArgsConstructor
public class InternalAuthCommandServiceImpl extends InternalUserServiceGrpc.InternalUserServiceImplBase {

    private final ApplicationContext applicationContext;
    private final BaseAggregate<UserAggregate, IUserCommand, UserRepository> userAggregateRepository;
    private final UserRepository userRepository;

    @Override
    public void createUser(InternalUserServiceOuterClass.CreateUserRequest request, StreamObserver<InternalUserServiceOuterClass.UserView> responseObserver) {
        GrpcHandlerUtils.handleInternal(responseObserver,
                () -> {
                    String userId = applicationContext.getBean(CreateUserFunc.class).runInternal(MappingUtils.mapObject(request, CreateUserRequest.class));
                    return InternalUserServiceOuterClass.UserView.newBuilder()
                            .setId(userId)
                            .setUsername(request.getUsername())
                            .build();
                });
    }

    @Override
    public void confirmUpdateUserPassword(InternalUserServiceOuterClass.ConfirmUpdateUserPasswordRequest request, StreamObserver<InternalUserServiceOuterClass.UserView> responseObserver) {
        GrpcHandlerUtils.handleInternal(responseObserver,
                () -> {
                    Date now = new Date();
                    UserAggregate userAggregate = userRepository.findByUsername(request.getUsername())
                            .orElseThrow(() -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));

                    if (ObjectUtils.notEqual(request.getNewPassword(), request.getConfirmPassword())) {
                        throw new ServiceException(AuthErrorCode.PASSWORD_NOT_MATCH);
                    }

                    UpdateUserPasswordCommand command = MappingUtils.mapObject(request, UpdateUserPasswordCommand.class);
                    command.setStatus(UserStatus.ACTIVE);
                    command.setPassword(request.getNewPassword());
                    command.setUpdatedDate(now);

                    userAggregateRepository.update(userAggregate.getId(), command);

                    return InternalUserServiceOuterClass.UserView.newBuilder()
                            .setId(userAggregate.getId())
                            .setUsername(userAggregate.getUsername())
                            .build();
                });
    }
}
