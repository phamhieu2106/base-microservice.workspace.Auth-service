package com.base.util;

import com.base.base.service.BaseService;
import com.base.constant.NotificationAuthMessage;
import com.base.request.api.InternalNotificationAPI;
import com.base.request.constant.NotificationType;
import com.base.request.domain.CreateNotificationRequest;
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
