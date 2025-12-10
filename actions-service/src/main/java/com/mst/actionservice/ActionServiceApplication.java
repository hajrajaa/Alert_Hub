package com.mst.actionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "com.mst.actionservice.client")
public class ActionServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ActionServiceApplication.class, args);
    }
}
