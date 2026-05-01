package com.project.devpulse.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

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

    private String consistency;

    private Instant lastFetched;
}