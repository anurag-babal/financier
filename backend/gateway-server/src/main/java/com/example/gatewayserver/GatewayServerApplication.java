package com.example.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/users/**")
//                        .filters(f -> f
//                                .rewritePath("/api/users/(?<segment>.*)", "/$\\{segment}")
//                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
//                        )
                        .uri("lb://user-service"))
                .route(p -> p
                        .path("/api/v1/auth/**")
                        .uri("lb://auth-service"))
                .route(p -> p
                        .path("/api/v1/transactions/**")
                        .uri("lb://transaction-service"))
                .route(p -> p
                        .path("/api/v1/expenses/**")
                        .uri("lb://expense-service"))
                .route(p -> p
                        .path("/api/v1/categories/**")
                        .uri("lb://expense-service"))
                .route(p -> p
                        .path("/api/v1/reports/**")
                        .uri("lb://report-service"))
                .build();
    }
}
