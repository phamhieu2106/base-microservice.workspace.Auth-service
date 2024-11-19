package com.henry.function;

import com.henry.base.func.BaseFunc;
import com.henry.repository.UserRepository;
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
