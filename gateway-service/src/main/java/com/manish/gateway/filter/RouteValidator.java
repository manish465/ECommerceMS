package com.manish.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

public class RouteValidator {
    private static final List<String> publicApiEndPoints = List.of(
            "/eureka/web",
            "/api/auth",
            "/api/user/register",
            "/api/user/login",
            "/api/school/public"
    );

    public static Predicate<ServerHttpRequest> isSecured =
            request -> publicApiEndPoints
                    .stream()
                    .noneMatch(uri -> request
                            .getURI()
                            .getPath()
                            .contains(uri));
}
