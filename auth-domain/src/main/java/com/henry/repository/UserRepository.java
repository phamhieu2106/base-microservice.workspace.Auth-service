package com.henry.repository;

import com.henry.aggregate.UserAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAggregate, String> {
    boolean existsByUsername(String username);

    Optional<UserAggregate> findByUsername(String username);

    Optional<UserAggregate> findByVersionAndId(String version, String id);
}
