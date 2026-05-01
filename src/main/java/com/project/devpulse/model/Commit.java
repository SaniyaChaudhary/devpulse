package com.project.devpulse.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "commits",
        indexes = {
                @Index(name = "idx_commit_user", columnList = "user_id"),
                @Index(name = "idx_commit_sha", columnList = "commit_sha"),
                @Index(name = "idx_commit_date", columnList = "committed_at")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_commit_sha", columnNames = "commit_sha")
        }
)
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Use LAZY to avoid unnecessary joins
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "repo_name", nullable = false, length = 255)
    private String repoName;

    @Column(name = "commit_message", nullable = false, length = 2000)
    private String commitMessage;

    @Column(name = "commit_sha", nullable = false, unique = true, length = 100)
    private String commitSha;

    @Column(name = "branch", length = 100)
    private String branch;

    @Column(name = "committed_at", nullable = false)
    private LocalDateTime committedAt;

    @Column(name = "language", length = 100)
    private String language;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}