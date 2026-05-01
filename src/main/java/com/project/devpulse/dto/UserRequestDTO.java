package com.project.devpulse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private Long githubId;
    private String githubUsername;
    private String email;
    private String avatarUrl;
    private String profileUrl;
}