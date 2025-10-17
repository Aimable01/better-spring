package com.aimable.week1core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, String>> adminDashboard() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to Admin Dashboard");
        response.put("access", "Full system access");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, String>> manageUsers() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Managing all users");
        response.put("action", "User management panel");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String id) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted: " + id);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

}
