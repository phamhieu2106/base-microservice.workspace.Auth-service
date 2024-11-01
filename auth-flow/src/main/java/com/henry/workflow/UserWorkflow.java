package com.henry.workflow;

import com.henry.base.BaseObjectLoggAble;
import com.henry.constant.WorkflowTopic;
import org.springframework.kafka.annotation.KafkaListener;

public class UserWorkflow extends BaseObjectLoggAble {

    @KafkaListener(topics = WorkflowTopic.AUTH_EVENT_TOPIC)
    public void createUserWorkflow(String message) {
        System.out.println("Received event - " + message);
        // Bạn có thể thêm log thêm chi tiết để kiểm tra
        System.out.println("Listener is active and ready to process messages");
    }
}
