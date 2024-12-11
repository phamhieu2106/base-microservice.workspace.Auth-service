package com.henry.util;

import com.henry.base.repository.HistoryRepository;
import com.henry.domain.entity.HistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class HistoryUtils {

    private final HistoryRepository historyRepository;

    public void saveHistory(String entityId, String entityCode, Class<?> clazz, Integer historyType, String content) {
        HistoryEntity historyEntity = HistoryEntity.builder()
                .entityId(entityId)
                .entityCode(entityCode)
                .entityName(clazz.getSimpleName())
                .type(historyType)
                .content(content)
                .createdDate(new Date())
                .build();
        historyRepository.save(historyEntity);
    }
}
