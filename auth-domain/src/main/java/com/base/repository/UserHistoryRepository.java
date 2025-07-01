package com.base.repository;

import com.base.domain.entity.HistoryEntity;
import com.base.entity.UserHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistoryEntity, String> {
    Page<HistoryEntity> findAllByEntityId(String entityId, Pageable pageable);
}
