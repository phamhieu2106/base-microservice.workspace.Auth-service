package com.henry.repository;

import com.henry.aggregate.UserAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAggregate, String> {
}
