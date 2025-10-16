package com.aimable.week1core.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class DebugFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("======== FILTER CHAIN DEBUG ========");
        log.info("Request URI : {}", request.getRequestURI());
        log.info("Request Method : {}", request.getMethod());
        log.info("Request Headers : {}", request.getHeaderNames());
        log.info("Request IP : {}", request.getRemoteAddr());

        // continue with the filter
        filterChain.doFilter(request, response);

        log.info("======== FILTER CHAIN COMPLETE ========");
    }
}
