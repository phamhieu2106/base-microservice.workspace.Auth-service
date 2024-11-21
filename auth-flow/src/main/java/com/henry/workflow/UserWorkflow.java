package com.henry.workflow;

import com.henry.base.workflow.BaseWorkFlow;
import com.henry.constant.AuthActionType;
import com.henry.constant.WorkflowTopic;
import com.henry.event.CreateUserEvent;
import com.henry.event.EventEntity;
import com.henry.event.UpdateUserEvent;
import com.henry.function.SyncUserViewFunc;
import com.henry.utils.EventEntityMapperUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class UserWorkflow extends BaseWorkFlow {

    @KafkaListener(topics = WorkflowTopic.AUTH_EVENT_TOPIC, groupId = WorkflowTopic.WORK_FLOW_GROUP_ID)
    @Retryable
    public void handleUserWorkflowEvent(String message) {
        logger.info(">>>>>>>>>>> UserWorkFlow received event <<<<<<<<<<<<");
        try {
            EventEntity eventEntity = EventEntityMapperUtils.mapDataToEventEntity(message);
            handleEvent(eventEntity);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleEvent(EventEntity eventEntity) {
        if (Objects.equals(CreateUserEvent.class.getSimpleName(), eventEntity.getEventType())) {
            logger.info(">>>>>>>>>>> CreateUserEvent with version: {} entityId: {} <<<<<<<<<<<", eventEntity.getId(), eventEntity.getEntityId());
            applicationContext.getBean(SyncUserViewFunc.class).exec(eventEntity.getEntityId(), eventEntity.getId(), AuthActionType.CREATED);
            return;
        } else if (Objects.equals(UpdateUserEvent.class.getSimpleName(), eventEntity.getEventType())) {
            logger.info(">>>>>>>>>>> UpdateUserEvent with version: {} entityId: {} <<<<<<<<<<<", eventEntity.getId(), eventEntity.getEntityId());
            applicationContext.getBean(SyncUserViewFunc.class).exec(eventEntity.getEntityId(), eventEntity.getId(), AuthActionType.UPDATED);
            return;
        }

        logger.info(">>>>>>>>>>> No handler found for event type: {}", eventEntity.getEventType());
    }
}
