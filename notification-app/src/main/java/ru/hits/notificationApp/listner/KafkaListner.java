package ru.hits.notificationApp.listner;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.message.MessageDTO;
import ru.hits.notificationApp.service.NotificationService;

@Service
@RequiredArgsConstructor
public class KafkaListner {
    private final NotificationService notificationService;
    @KafkaListener(topics = "registration", groupId = "registration")
    public void userNotification(MessageDTO notificationDTO){

    }
}
