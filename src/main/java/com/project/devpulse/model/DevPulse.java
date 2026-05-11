package com.project.devpulse.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "devpulse")
public class DevPulse {

    @Id
    @Indexed(unique = true)
    private String username;

    private String avatarUrl;
    private int totalRepos;
    private String mostUsedLanguage;
    private int totalCommitsLast30Days;
    private int activityScore;
    private String repoVolumeTier;
    private Instant lastFetched;

    // Phase 1 additions
    private int followers;
    private int totalStars;
    private int totalForks;
    private String mostStarredRepo;
    private String lastActiveDate;      // most recent pushed_at
    private String accountCreatedAt;    // user created_at
    private int accountAgeYears;
    private double repoGrowthRate;      // repos / account age in years
    private int recentlyActiveRepos;    // pushed_at within last 30 days
    private Map<String, Long> languageDistribution; // {"Java": 5, "Python": 3}
    private String developerTier;       // Hobbyist / Active / Power Dev
}