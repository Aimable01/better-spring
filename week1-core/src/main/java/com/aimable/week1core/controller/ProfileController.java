package com.aimable.week1core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @GetMapping("/profile")
    public ResponseEntity<Map<String,Object>> getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String,Object> profile = new HashMap<>();
        profile.put("username",authentication.getName());
        profile.put("authorities",authentication.getAuthorities());
        profile.put("email",authentication.getPrincipal());
        profile.put("authenticated",authentication.isAuthenticated());

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
