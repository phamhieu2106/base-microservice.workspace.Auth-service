package com.base.grpc;

import com.base.func.user.CreateUserFunc;
import com.base.request.CreateUserRequest;
import com.base.util.MappingUtils;
import com.base.utils.GrpcHandlerUtils;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@GrpcService
@Component
@RequiredArgsConstructor
public class InternalAuthCommandServiceImpl extends InternalUserServiceGrpc.InternalUserServiceImplBase {

    private final ApplicationContext applicationContext;

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
}
