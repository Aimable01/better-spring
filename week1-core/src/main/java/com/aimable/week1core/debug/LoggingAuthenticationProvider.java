package com.aimable.week1core.debug;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final AuthenticationLogger authenticationLogger;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("🔄 AuthenticationProvider.authenticate() called");
        log.info("  - Input Authentication: {}", authentication);

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        authenticationLogger.logAuthenticationAttempt(username);

        // load the user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.info("   - Loaded UserDetails: {}", userDetails.getUsername());

        // verify password
        if (!userDetails.getPassword().equals(password)) {
            authenticationLogger.logAuthenticationFailure(new BadCredentialsException("Invalid password"));
            throw new BadCredentialsException("Invalid password");
        }

        // create successful authentication
        Authentication successfulAuth = new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
        );

        authenticationLogger.logAuthenticationSuccess(successfulAuth);
        return successfulAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
