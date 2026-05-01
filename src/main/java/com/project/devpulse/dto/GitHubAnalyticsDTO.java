package com.project.devpulse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubAnalyticsDTO {

    // 🔹 Identity
    private String username;
    private Long githubId;

    // 🔹 Repo stats
    private Integer totalRepos;
    private Integer totalStars;
    private Integer totalForks;

    // 🔹 Language insight
    private String mostUsedLanguage;

    // 🔹 Activity insight
    private Integer totalCommits;

    // 🔹 Advanced metrics (your “smart layer”)
    private Double profileScore;
    private String activityLevel; // LOW, MEDIUM, HIGH
}