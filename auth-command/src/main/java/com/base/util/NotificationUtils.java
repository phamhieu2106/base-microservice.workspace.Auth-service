package com.base.util;

import com.base.constant.NotificationAuthMessage;
import com.base.constant.NotificationType;
import com.base.domain.CreateNotificationRequest;
import com.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationUtils extends BaseService {


    public void createNotificationBlockUser(String username) {
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setMessage(NotificationAuthMessage.NOTIFICATION_BLOCKED_USER);
        request.setRecipient(username);
        request.setType(NotificationType.SINGLE);
    }
}
