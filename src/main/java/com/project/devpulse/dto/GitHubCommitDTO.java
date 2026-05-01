package com.project.devpulse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GitHubCommitDTO {

    @JsonProperty("sha")
    private String sha;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("commit")
    private CommitDetail commit;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommitDetail {

        @JsonProperty("message")
        private String message;

        @JsonProperty("author")
        private CommitAuthor author;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommitAuthor {

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("date")
        private OffsetDateTime date;
    }
}