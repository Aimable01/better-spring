package com.aimable.week1core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    @GetMapping("/content")
    public ResponseEntity<Map<String, String>> moderateContent() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Content moderation panel");
        response.put("access", "Can moderate posts and comments");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/posts/review")
    public ResponseEntity<Map<String, String>> reviewPost() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Post under review");
        response.put("action", "Review completed");
        return ResponseEntity.ok(response);
    }

}
