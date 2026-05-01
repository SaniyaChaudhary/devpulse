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
        name = "weekly_reports",
        indexes = {
                @Index(name = "idx_report_user", columnList = "user_id"),
                @Index(name = "idx_week_start", columnList = "week_start_date")
        }
)
public class WeeklyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 Important: LAZY loading for performance
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "report_content", columnDefinition = "TEXT")
    private String reportContent;

    @Column(name = "total_commits")
    private Integer totalCommits;

    @Column(name = "active_days")
    private Integer activeDays;

    @Column(name = "most_active_day", length = 50)
    private String mostActiveDay;

    @Column(name = "top_language", length = 100)
    private String topLanguage;

    @Column(name = "top_repository", length = 255)
    private String topRepository;

    @Column(name = "week_start_date", nullable = false)
    private LocalDateTime weekStartDate;

    @Column(name = "week_end_date", nullable = false)
    private LocalDateTime weekEndDate;

    // 🔥 Modern Hibernate way (no @PrePersist needed)
    @org.hibernate.annotations.CreationTimestamp
    @Column(name = "generated_at", updatable = false)
    private LocalDateTime generatedAt;
}