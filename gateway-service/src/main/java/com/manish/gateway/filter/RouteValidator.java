package com.manish.gateway.filter;

import java.util.List;

public class RouteValidator {
    private static final List<String> publicApiEndPoints = List.of(
            "/eureka/web",
            "/api/auth",
            "/api/user/register",
            "/api/user/login",
            "/api/school/public"
    );
}
