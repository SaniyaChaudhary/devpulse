package com.project.devpulse.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DevPulseResponseDTO {

    private String username;
    private String avatarUrl;

    private int totalRepos;
    private String mostUsedLanguage;
    private Map<String, Long> languageDistribution;

    private int activityScore;
    private String repoVolumeTier;
    private String developerTier;

    // Phase 1 additions
    private int followers;
    private int totalStars;
    private int totalForks;
    private String mostStarredRepo;
    private String lastActiveDate;
    private String accountCreatedAt;
    private int accountAgeYears;
    private double repoGrowthRate;
    private int recentlyActiveRepos;
}