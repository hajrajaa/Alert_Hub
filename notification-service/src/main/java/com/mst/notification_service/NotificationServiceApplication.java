package com.mst.notification_service;

import com.mst.notification_service.dto.NotificationDTO;
import com.mst.notification_service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class NotificationServiceApplication {



	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
