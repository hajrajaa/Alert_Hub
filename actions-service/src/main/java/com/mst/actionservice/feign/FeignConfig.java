package com.mst.actionservice.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class FeignConfig {

    @Bean
    public FeignInterceptor feignHeaderInterceptor() {
        return new FeignInterceptor();
    }
}