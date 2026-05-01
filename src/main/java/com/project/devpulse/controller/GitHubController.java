//package com.project.devpulse.controller;
//
//import com.project.devpulse.dto.*;
//import com.project.devpulse.service.GitHubService;
//import com.project.devpulse.service.AnalyticsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/github")
//@RequiredArgsConstructor
//public class GitHubController {
//
//    private final GitHubService gitHubService;
//    private final AnalyticsService analyticsService;
//
//
//    @GetMapping("/profile/{username}")
//    public ResponseEntity<GitHubUserDTO> getProfile(@PathVariable String username) {
//        return ResponseEntity.ok(gitHubService.fetchUser(username));
//    }
//
//    @GetMapping("/users/{username}/repos")
//    public ResponseEntity<List<GitHubRepoDTO>> getRepos(@PathVariable String username) {
//        return ResponseEntity.ok(gitHubService.fetchRepos(username));
//    }
//}