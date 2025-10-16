package com.aimable.week1core.service;

import com.aimable.week1core.debug.AuthenticationFlowTracer;
import com.aimable.week1core.dto.LoginRequest;
import com.aimable.week1core.dto.RegisterRequest;
import com.aimable.week1core.entity.User;
import com.aimable.week1core.enums.Role;
import com.aimable.week1core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationFlowTracer authenticationFlowTracer;

    public User register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(Role.USER));

        return userRepository.save(user);
    }

    public Map<String,Object> login(LoginRequest request){

/**
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
*/
        // using the authentication flow tracer
        authenticationFlowTracer.traceFullAuthenticationFlow(request.getEmail(), request.getPassword());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String,Object> response = new HashMap<>();
        response.put("message","Login successful");
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("authenticated", authentication.isAuthenticated());

        return response;
    }
}
