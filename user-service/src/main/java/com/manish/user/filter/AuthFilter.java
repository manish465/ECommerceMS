package com.manish.user.filter;

import com.manish.user.utils.Convertor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
public class AuthFilter extends OncePerRequestFilter {
    private final List<String> skipUrls = List.of("/user/register", "/user/login", "/user/delete/all");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        log.info("|| doFilterInternal is called from AuthFilter class ||");

        String email = "ms2@gmail.com";
        String rolesString = "NORMAL";
        Collection<GrantedAuthority> authorities = Convertor.extractAuthoritiesFromString(rolesString);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
                authorities);
        log.info("|| using email : {} and role : {} ||", email, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return skipUrls.stream().anyMatch(path -> pathMatcher.match(path, request.getRequestURI()));
    }
}
