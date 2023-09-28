package com.manish.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

public class RouteValidator {
    private static final List<String> publicApiEndPoints = List.of(
            "/user/register",
            "/user/login",
            "/user/delete/all",
            "/user/all"
    );

    public static Predicate<ServerHttpRequest> isSecured =
            request -> publicApiEndPoints
                    .stream()
                    .noneMatch(uri -> request
                            .getURI()
                            .getPath()
                            .contains(uri));
}
