package com.project.devpulse.dto;

import lombok.Data;

@Data
public class GitHubUserDTO {
    private String login;
    private String avatar_url;
    private int followers;           // add this
    private int public_repos;
    private String created_at;       // add this — account age
}