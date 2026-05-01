package com.project.devpulse.service;

import com.project.devpulse.dto.DevPulseResponseDTO;
import com.project.devpulse.dto.GitHubRepoDTO;
import com.project.devpulse.dto.GitHubUserDTO;

import com.project.devpulse.mapper.DevPulseMapper;
import com.project.devpulse.model.DevPulse;
import com.project.devpulse.repository.DevPulseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final GitHubService gitHubService;
    private final DevPulseRepository repository;
    private  final DevPulseMapper mapper;

    public Mono<DevPulseResponseDTO> buildDevPulse(String username) {

        return repository.findById(username)
                .filter(saved ->
                        saved.getLastFetched() != null &&
                                saved.getLastFetched().isAfter(
                                        Instant.now().minus(Duration.ofMinutes(30))
                                )
                )
                .map(mapper::toResponseDTO)
                .switchIfEmpty(fetchFromGitHub(username));
    }

    private Mono<DevPulseResponseDTO> fetchFromGitHub(String username) {

        Mono<GitHubUserDTO> userMono = gitHubService.fetchUser(username);
        Flux<GitHubRepoDTO> repoFlux = gitHubService.fetchRepos(username);

        return userMono.flatMap(user ->
                repoFlux.collectList().flatMap(repos -> {

                    DevPulse entity = buildEntity(user, repos);

                    return repository.save(entity)
                            .map(mapper::toResponseDTO); // ✅ fixed
                })
        );
    }
    private DevPulse buildEntity(GitHubUserDTO user, List<GitHubRepoDTO> repos) {

        return DevPulse.builder()
                .username(user.getLogin())
                .avatarUrl(user.getAvatar_url())
                .totalRepos(repos.size())
                .mostUsedLanguage(findTopLanguage(repos))
                .activityScore(calculateActivityScore(repos))
                .consistency(calculateConsistency(repos))
                .lastFetched(Instant.now())
                .build();
    }



    private int calculateActivityScore(List<GitHubRepoDTO> repos) {

        if (repos.isEmpty()) return 0;

        Map<String, Long> languageCount = repos.stream()
                .map(GitHubRepoDTO::getLanguage)
                .filter(lang -> lang != null && !lang.isBlank())
                .collect(Collectors.groupingBy(lang -> lang, Collectors.counting()));

        long topLanguageCount = languageCount.values().stream()
                .max(Long::compare)
                .orElse(0L);

        return (int) ((repos.size() * 5) + (topLanguageCount * 10));
    }

    private String findTopLanguage(List<GitHubRepoDTO> repos) {

        return repos.stream()
                .map(GitHubRepoDTO::getLanguage)
                .filter(lang -> lang != null && !lang.isBlank())
                .collect(Collectors.groupingBy(
                        lang -> lang,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }
    private String calculateConsistency(List<GitHubRepoDTO> repos) {

        int repoCount = repos.size();

        return repoCount > 50 ? "HIGH" :
                repoCount > 20 ? "MEDIUM" : "LOW";
    }
}