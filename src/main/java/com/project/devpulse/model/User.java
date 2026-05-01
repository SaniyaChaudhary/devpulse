package com.project.devpulse.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_github_id", columnList = "github_id"),
                @Index(name = "idx_email", columnList = "email"),
                @Index(name = "idx_github_username", columnList = "github_username")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_github_id", columnNames = "github_id"),
                @UniqueConstraint(name = "uk_email", columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_id", nullable = false)
    private Long githubId;

    @Column(name = "github_username", nullable = false, length = 100)
    private String githubUsername;

    @Column(length = 150)
    private String email;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "profile_url", length = 500)
    private String profileUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;
}