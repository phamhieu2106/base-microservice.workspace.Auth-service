package com.base.repository;

import com.base.aggregate.UserAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAggregate, String> {
    boolean existsByUsername(String username);

    Optional<UserAggregate> findByUsername(String username);

    Optional<UserAggregate> findByUsernameAndPasswordAndStatusIsNot(String username, String password, Integer status);

    Optional<UserAggregate> findByIdAndVersion(String id, String version);
}
