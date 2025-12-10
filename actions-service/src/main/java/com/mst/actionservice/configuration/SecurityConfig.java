package com.mst.actionservice.configuration;

import com.mst.actionservice.security.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityFilter securityFilter) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/actions/create").hasAnyRole("ADMIN", "CREATE_ACTION")
                            .requestMatchers("/actions/update/**").hasAnyRole("ADMIN", "UPDATE_ACTION")
                            .requestMatchers("/actions/enable/**").hasAnyRole("ADMIN", "UPDATE_ACTION")
                            .requestMatchers("/actions/disable/**").hasAnyRole("ADMIN", "UPDATE_ACTION")
                            .requestMatchers("/actions/delete/**").hasAnyRole("ADMIN", "DELETE_ACTION")
                            .requestMatchers("/actions//manual-trigger/**").hasAnyRole("ADMIN", "TRIGGER_PROCESS")
                            .requestMatchers(HttpMethod.GET, "/actions/**").hasAnyRole("READ")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless (no session creation)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }
}
