package com.project.devpulse.service;

import com.project.devpulse.dto.GitHubRepoDTO;
import com.project.devpulse.dto.GitHubUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final WebClient webClient;

    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final int MAX_RETRY = 2;

    // =========================
    // 1. USER PROFILE
    // =========================
    public Mono<GitHubUserDTO> fetchUser(String username) {
        return webClient.get()
                .uri("/users/{username}", username)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> Mono.error(new RuntimeException(
                                "GitHub user not found: " + username
                        ))
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException(
                                "GitHub server error while fetching user: " + username
                        ))
                )
                .bodyToMono(GitHubUserDTO.class)
                .timeout(TIMEOUT)
                .retry(MAX_RETRY);
    }

    // =========================
    // 2. USER REPOSITORIES
    // =========================
    public Flux<GitHubRepoDTO> fetchRepos(String username) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/{username}/repos")
                        .queryParam("per_page", 100)
                        .build(username))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> Mono.error(new RuntimeException(
                                "Repos not found for user: " + username
                        ))
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException(
                                "GitHub server error while fetching repos: " + username
                        ))
                )
                .bodyToFlux(GitHubRepoDTO.class)
                .timeout(TIMEOUT)
                .retry(MAX_RETRY);
    }
}