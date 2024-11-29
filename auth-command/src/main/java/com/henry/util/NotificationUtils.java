package com.henry.util;

import com.henry.base.service.BaseService;
import com.henry.constant.NotificationAuthMessage;
import com.henry.request.api.InternalNotificationAPI;
import com.henry.request.constant.NotificationType;
import com.henry.request.domain.CreateNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationUtils extends BaseService {

    private final InternalNotificationAPI internalNotificationAPI;

    public void createNotificationBlockUser(String username) {
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setMessage(NotificationAuthMessage.NOTIFICATION_BLOCKED_USER);
        request.setRecipient(username);
        request.setType(NotificationType.SINGLE);

        internalNotificationAPI.createNotification(request);
    }
}
