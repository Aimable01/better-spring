package com.aimable.week1core.debug;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationLogger {

    public void logAuthenticationAttempt(String username){
        log.info("🔏 Authentication attempt for user '{}'", username);
        logCurrentThread();
    }

    public void logAuthenticationSuccess(Authentication authentication){
        log.info("✅ Authentication success for user: {}", authentication.getName());
        log.info("  - Authorities: {}", authentication.getAuthorities());
        log.info("  - Principal: {}", authentication.getPrincipal());
        log.info("  - Credentials: {}", authentication.getCredentials());
        log.info("  - Authenticated: {}", authentication.isAuthenticated());
    }

    public void logAuthenticationFailure(AuthenticationException authenticationException){
        log.info("❌ AUTHENTICATION FAILED: {}", authenticationException.getMessage());
    }

    public void logSecurityContext(String operation){
        var context = SecurityContextHolder.getContext();
        var auth = context.getAuthentication();

        log.info("🛡️ Security context: {}", operation);
        log.info("  - Authentication: {}", auth);
        if(auth != null){
            log.info("  - User: {}", auth.getName());
            log.info("  - Authenticated: {}", auth.isAuthenticated());
        }
        logCurrentThread();
    }

    public void logCurrentThread(){
        log.info("  - Thread: {} (ID: {})", Thread.currentThread().getName(), Thread.currentThread().getId());
    }
}
