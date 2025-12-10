package com.mst.api_gateway.security;

import com.mst.api_gateway.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

@Component
public class JwtFilter implements WebFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return extractUserIdAndEmail(exchange)
                .flatMap(entry -> {
                    String userId = entry.getKey();
                    String email = entry.getValue();

                    return getRoles(Long.parseLong(userId))
                            .flatMap(roles -> {
                                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                        .header("X-User-Id", userId)
                                        .header("X-User-Roles", String.join(",", roles))
                                        .header("X-User-Email", email)
                                        .build();
                                ServerWebExchange mutatedExchange = exchange.mutate()
                                        .request(mutatedRequest)
                                        .build();
                                return chain.filter(mutatedExchange);
                            });
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    private Mono<Map.Entry<String, String>> extractUserIdAndEmail(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Claims claims = tokenService.extractAllClaims(token);
                String userId = String.valueOf(claims.get("id", Long.class));
                String email = claims.get("email", String.class);

                if (isNumeric(userId)) {
                    return Mono.just(new SimpleEntry<>(userId, email));
                } else {
                    return Mono.error(new IllegalArgumentException("UserId is not numeric"));
                }
            } catch (Exception e) {
                return Mono.empty();
            }
        }
        return Mono.empty();
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Mono<List<String>> getRoles(Long userId) {
        String redisKey = "user:roles:" + userId;

        return reactiveRedisTemplate.opsForValue().get(redisKey)
                .flatMap(obj -> {
                    if (obj instanceof List<?> list) {
                        List<String> roles = list.stream()
                                .map(String::valueOf)
                                .collect(Collectors.toList());
                        return Mono.just(roles);
                    }
                    return Mono.empty();
                })
                .switchIfEmpty(
                        webClient.get()
                                .uri("http://security-service:8081/auth/{userId}/roles", userId)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                                .flatMap(roles ->
                                        reactiveRedisTemplate.opsForValue()
                                                .set(redisKey, roles, Duration.ofMinutes(5))
                                                .thenReturn(roles)
                                )
                );
    }
}
