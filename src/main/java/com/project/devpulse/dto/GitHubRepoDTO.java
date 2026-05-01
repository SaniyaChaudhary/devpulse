package com.project.devpulse.dto;

import lombok.Data;

@Data
public class GitHubRepoDTO {

    private String name;

    private Integer stars;   // ✅ FIX: use wrapper type
    private Integer forks;   // ✅ FIX: use wrapper type

    private String language;
}