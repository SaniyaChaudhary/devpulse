//package com.project.devpulse.service;
//
//import com.project.devpulse.dto.UserRequestDTO;
//import com.project.devpulse.model.User;
//import com.project.devpulse.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    @Transactional
//    public User upsertUser(UserRequestDTO dto) {
//
//        return userRepository.findByGithubId(dto.getGithubId())
//                .map(user -> updateUser(user, dto))
//                .orElseGet(() -> createUser(dto));
//    }
//
//    private User updateUser(User user, UserRequestDTO dto) {
//
//        if (dto.getGithubUsername() != null) {
//            user.setGithubUsername(dto.getGithubUsername());
//        }
//
//        if (dto.getEmail() != null) {
//            user.setEmail(dto.getEmail());
//        }
//
//        if (dto.getAvatarUrl() != null) {
//            user.setAvatarUrl(dto.getAvatarUrl());
//        }
//
//        if (dto.getProfileUrl() != null) {
//            user.setProfileUrl(dto.getProfileUrl());
//        }
//
//        user.setLastSyncedAt(LocalDateTime.now());
//
//        return userRepository.save(user);
//    }
//
//    private User createUser(UserRequestDTO dto) {
//
//        User user = User.builder()
//                .githubId(dto.getGithubId())
//                .githubUsername(dto.getGithubUsername())
//                .email(dto.getEmail())
//                .avatarUrl(dto.getAvatarUrl())
//                .profileUrl(dto.getProfileUrl())
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        return userRepository.save(user);
//    }
//
//    public User getUserByGithubId(Long githubId) {
//        return userRepository.findByGithubId(githubId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    public User getUserByUsername(String username) {
//        return userRepository.findByGithubUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//}