package com.aimable.week1core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/posts")
    public ResponseEntity<Map<String, String>> getPosts() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Public posts retrieved");
        response.put("count", "15 posts");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/posts/create")
    public ResponseEntity<Map<String, String>> createPost() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Post created successfully");
        response.put("status", "published");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/posts/delete/{id}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable String id) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Post deleted: " + id);
        response.put("status", "deleted");
        return ResponseEntity.ok(response);
    }

}
