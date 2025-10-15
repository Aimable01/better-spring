package com.aimable.week1core.controller;

import com.aimable.week1core.dto.RegisterRequest;
import com.aimable.week1core.entity.User;
import com.aimable.week1core.repository.UserRepository;
import com.aimable.week1core.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){
        User user = authService.register(request);
        return ResponseEntity.ok(user.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(){
        return ResponseEntity.ok("Login success");
    }
}
