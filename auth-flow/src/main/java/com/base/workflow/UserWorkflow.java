package com.base.workflow;

import com.base.common.annotation.BaseEventSubscribeHandler;
import com.base.constant.AuthActionType;
import com.base.constant.WorkflowTopic;
import com.base.event.*;
import com.base.function.SyncUserViewFunc;
import com.base.utils.EventEntityMapperUtils;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;
import java.util.Objects;

@BaseEventSubscribeHandler(groupId = "auth-base-user-workflow-group")
public class UserWorkflow extends BaseWorkFlow {

    @KafkaListener(topics = WorkflowTopic.AUTH_EVENT_TOPIC, groupId = WorkflowTopic.WORK_FLOW_GROUP_ID)
    public void handleUserWorkflowEvent(String message) {
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
        } else if (Objects.equals(UpdateUserPasswordEvent.class.getSimpleName(), eventEntity.getEventType())) {
            logger.info(">>>>>>>>>>> UpdateUserPasswordEvent with version: {} entityId: {} <<<<<<<<<<<", eventEntity.getId(), eventEntity.getEntityId());
            applicationContext.getBean(SyncUserViewFunc.class).exec(eventEntity.getEntityId(), eventEntity.getId(), AuthActionType.UPDATED);
            return;
        } else if (Objects.equals(BlockUserEvent.class.getSimpleName(), eventEntity.getEventType())) {
            logger.info(">>>>>>>>>>> BlockUserEvent with version: {} entityId: {} <<<<<<<<<<<", eventEntity.getId(), eventEntity.getEntityId());
            applicationContext.getBean(SyncUserViewFunc.class).exec(eventEntity.getEntityId(), eventEntity.getId(), AuthActionType.BLOCKED);
            return;
        }

        logger.info(">>>>>>>>>>> No handler found for event type: {}", eventEntity.getEventType());
    }
}
