package com.project.devpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String githubId;

    private String username;
    private String email;
    private String avatarUrl;
    private String profileUrl;

    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        lastLoginAt = LocalDateTime.now();
    }
}