package com.aimable.week1core.debug;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFlowTracer {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationLogger authenticationLogger;

    public void traceFullAuthenticationFlow(String username, String password) {
        log.info("\n" +
                "🚀 ========== AUTHENTICATION FLOW START ==========\n" +
                "📥 Step 1: Request received with credentials\n" +
                "   - Username: {}\n" +
                "   - Password: [PROTECTED]\n" +
                "================================================", username);

        // Step 2: Create Authentication token (unauthenticated)
        UsernamePasswordAuthenticationToken authenticationRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        log.info("\n" +
                        "🪙 Step 2: Created Authentication Token\n" +
                        "   - Token: {}\n" +
                        "   - Authenticated: {}\n" +
                        "   - Principal: {}\n" +
                        "   - Credentials: [PROTECTED]\n" +
                        "================================================",
                authenticationRequest.getClass().getSimpleName(),
                authenticationRequest.isAuthenticated(),
                authenticationRequest.getPrincipal());

        // Step 3: AuthenticationManager processes the request
        log.info("\n" +
                        "🔧 Step 3: AuthenticationManager processing\n" +
                        "   - Manager: {}\n" +
                        "================================================",
                authenticationManager.getClass().getSimpleName());

        // Step 4: Authenticate (this goes through ProviderManager -> AuthenticationProvider)
        Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);

        log.info("\n" +
                        "✅ Step 4: Authentication Result\n" +
                        "   - Result: {}\n" +
                        "   - Authenticated: {}\n" +
                        "   - Principal: {}\n" +
                        "   - Authorities: {}\n" +
                        "================================================",
                authenticationResult.getClass().getSimpleName(),
                authenticationResult.isAuthenticated(),
                authenticationResult.getPrincipal(),
                authenticationResult.getAuthorities());

        // Step 5: Set in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);

        authenticationLogger.logSecurityContext("SET");

        log.info("\n" +
                        "🏁 ========== AUTHENTICATION FLOW COMPLETE ==========\n" +
                        "   - User: {} is now authenticated\n" +
                        "   - SecurityContext populated\n" +
                        "   - Thread: {}\n" +
                        "================================================\n",
                username, Thread.currentThread().getName());
    }
}
