package com.project.devpulse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    // 🔹 Identity Info
    private String githubId;
    private String username;
    private String email;
    private String avatarUrl;
    private String profileUrl;

    // 🔹 Contribution Stats
    private Integer totalCommits;
    private Integer totalRepos;
    private Integer currentStreak;
    private Integer longestStreak;
    private String topLanguage;
    private String mostActiveDay;
}