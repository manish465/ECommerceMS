package com.manish.user.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Convertor {
    public static String extractAuthoritiesToString(Collection<? extends GrantedAuthority> authorities){
        StringBuilder roles = new StringBuilder();

        for(GrantedAuthority authority : authorities){
            String authorityString = authority.toString();

            if(!roles.isEmpty())
                roles.append(",");
            roles.append(authorityString);
        }

        return roles.toString();
    }

    public static Collection<GrantedAuthority> extractAuthoritiesFromString(String roles){
        return Arrays
                .stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
