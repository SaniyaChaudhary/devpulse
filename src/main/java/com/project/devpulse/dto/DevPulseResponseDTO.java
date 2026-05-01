package com.project.devpulse.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DevPulseResponseDTO {

    private String username;
    private String avatarUrl;

    private int totalRepos;
    private String mostUsedLanguage;

    private int activityScore;
    private String consistency;

}