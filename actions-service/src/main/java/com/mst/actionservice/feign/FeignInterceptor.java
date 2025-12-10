package com.mst.actionservice.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            String rolesHeader = request.getHeader("X-User-Roles");
            String userIdHeader = request.getHeader("X-User-Id");

            if (rolesHeader != null) {
                requestTemplate.header("X-User-Roles", rolesHeader);
            }
            if (userIdHeader != null) {
                requestTemplate.header("X-User-Id", userIdHeader);
            }
        }
    }
}
