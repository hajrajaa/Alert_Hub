package com.mst.notification_service.service;

import com.mst.notification_service.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    MailService mailService;

    @KafkaListener(topics = "email", groupId = "${spring.kafka.consumer.group-id}")
    public void listenEmail(NotificationDTO notificationDTO) {
        System.out.println(notificationDTO);
//        mailService.send(notificationDTO);
    }

    @KafkaListener(topics = "sms", groupId = "${spring.kafka.consumer.group-id}")
    public void listenPhone(NotificationDTO notificationDTO) {  }
}
