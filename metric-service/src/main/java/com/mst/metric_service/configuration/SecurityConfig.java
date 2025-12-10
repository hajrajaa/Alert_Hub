package com.mst.metric_service.configuration;

import com.mst.metric_service.security.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   SecurityFilter securityFilter) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/metrics/create").hasAnyRole("ADMIN","CREATE_METRIC")
                            .requestMatchers("/metrics/delete/**").hasAnyRole("ADMIN","DELETE_METRIC")
                            .requestMatchers("/metrics/update/**").hasAnyRole("ADMIN","UPDATE_METRIC")
                            .requestMatchers("/metrics/check").hasAnyRole("ADMIN","CREATE_ACTION","UPDATE_ACTION")
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
