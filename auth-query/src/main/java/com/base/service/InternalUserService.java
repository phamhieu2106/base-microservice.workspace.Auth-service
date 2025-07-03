package com.base.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class InternalUserService extends BaseService{

    @GrpcClient("auth-query")
    private InternalAuthServiceGrpc.InternalAuthServiceBlockingStub authServiceBlockingStub;

    public User.UserView findByUsername(String username) {
        User.FindByUsernameRequest internalRequest = User.FindByUsernameRequest.newBuilder()
                .setUsername(username)
                .build();
        return authServiceBlockingStub.findUserByUsername(internalRequest);
    }
}
