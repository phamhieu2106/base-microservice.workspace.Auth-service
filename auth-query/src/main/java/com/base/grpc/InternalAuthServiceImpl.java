package com.base.grpc;

import com.base.aggregate.UserAggregate;
import com.base.constant.AuthErrorCode;
import com.base.exception.ServiceException;
import com.base.repository.UserRepository;
import com.base.util.MappingUtils;
import com.base.utils.GrpcHandlerUtils;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class InternalAuthServiceImpl extends InternalUserServiceGrpc.InternalUserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void findUserByUsername(InternalUserServiceOuterClass.FindByUsernameRequest request,
                                   StreamObserver<InternalUserServiceOuterClass.UserView> responseObserver) {
        GrpcHandlerUtils.handle(responseObserver, () -> {
            UserAggregate userAggregate = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ServiceException(AuthErrorCode.USER_NOT_FOUND));
            return MappingUtils.mapObject(userAggregate, InternalUserServiceOuterClass.UserView.class);
        });
    }
}
