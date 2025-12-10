package com.mst.notification_service.service;

import com.mst.notification_service.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void send(NotificationDTO notificationDTO){
        if(notificationDTO == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "The notification is Invalid");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("Alert_Hub");
        simpleMailMessage.setTo(notificationDTO.to());
        simpleMailMessage.setSubject("Alert Hub Notification");
        simpleMailMessage.setText(notificationDTO.message());
        javaMailSender.send(simpleMailMessage);
    }
}
