package com.project.devpulse.dto;

import lombok.Data;

@Data
public class GitHubRepoDTO {
    private String name;
    private String language;
    private int stargazers_count;    // add this
    private int forks_count;         // add this
    private String pushed_at;        // add this
    private String created_at;       // add this
}