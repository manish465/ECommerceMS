package com.manish.gateway.filter;

import com.manish.gateway.dto.ClaimsDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if(RouteValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Invalid access");
                }

                String authHeader = Objects.requireNonNull(exchange
                        .getRequest()
                        .getHeaders()
                        .get(HttpHeaders.AUTHORIZATION)
                ).get(0);

                if (authHeader.startsWith("Bearer ")){
                    String token = authHeader.substring(7);

                    WebClient client = WebClient.builder()
                            .baseUrl("http://localhost:2000/auth")
                            .build();

                    return client.get()
                            .uri("/validate?token=" + token)
                            .retrieve().bodyToMono(ClaimsDataDTO.class)
                            .map(response -> {
                                log.info("|| got data {} and {} in AuthenticationFilter ||", response.getUsername(), response.getRoles());

                                exchange.getRequest().mutate().header("x-forward-username", response.getUsername());
                                exchange.getRequest().mutate().header("x-forward-role", response.getRoles());

                                return exchange;
                            }).flatMap(chain::filter).onErrorResume(error -> {
                                System.out.println(error.getMessage());

                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                exchange.getResponse().setComplete();

                                return exchange.cleanupMultipart();
                            });
                }
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
