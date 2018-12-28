package com.example.demooauth2gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final TokenRelayGatewayFilterFactory filterFactory;

    public GatewayConfig(TokenRelayGatewayFilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("resource", r -> r.path("/get")
                .filters(f -> f.filter(filterFactory.apply()))
                .uri("http://httpbin.org"))
            .route("userinfo", r -> r.path("/userinfo")
                .filters(f -> f.filter(filterFactory.apply()))
                .uri("https://tour-uaa.cfapps.io/userinfo"))
            .build();
    }
}
