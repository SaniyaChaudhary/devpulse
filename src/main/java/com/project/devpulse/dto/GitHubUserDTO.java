package com.project.devpulse.dto;

import lombok.Data;

@Data
public class GitHubUserDTO {

    private String login;
    private Long id;
    private String avatar_url;
    private Integer followers;   // safer for null handling
    private Integer following;
}