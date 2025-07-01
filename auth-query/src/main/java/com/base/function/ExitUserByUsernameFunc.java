package com.base.function;

import com.base.base.func.BaseFunc;
import com.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExitUserByUsernameFunc extends BaseFunc {

    private final UserRepository userRepository;

    public Boolean exec(String username) {
        return userRepository.existsByUsername(username);
    }
}
