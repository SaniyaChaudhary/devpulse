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
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final GitHubService gitHubService;
    private final DevPulseRepository repository;
    private final DevPulseMapper mapper;

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
        return Mono.zip(
                        gitHubService.fetchUser(username),
                        gitHubService.fetchRepos(username).collectList()
                )
                .flatMap(tuple -> {
                    DevPulse entity = buildEntity(tuple.getT1(), tuple.getT2());
                    return repository.save(entity).map(mapper::toResponseDTO);
                });
    }

    private DevPulse buildEntity(GitHubUserDTO user, List<GitHubRepoDTO> repos) {

        // compute once, reuse everywhere
        Map<String, Long> languageDist = computeLanguageDistribution(repos);
        String topLanguage = findTopLanguage(languageDist);
        int totalStars = repos.stream().mapToInt(GitHubRepoDTO::getStargazers_count).sum();
        int totalForks = repos.stream().mapToInt(GitHubRepoDTO::getForks_count).sum();
        String mostStarredRepo = findMostStarredRepo(repos);
        String lastActiveDate = findLastActiveDate(repos);
        int recentlyActiveRepos = countRecentlyActiveRepos(repos);
        int accountAgeYears = calculateAccountAge(user.getCreated_at());
        double repoGrowthRate = accountAgeYears > 0
                ? (double) repos.size() / accountAgeYears
                : repos.size();
        int activityScore = calculateActivityScore(
                repos.size(), totalStars, recentlyActiveRepos, languageDist.size()
        );
        String developerTier = computeDeveloperTier(activityScore);

        return DevPulse.builder()
                .username(user.getLogin())
                .avatarUrl(user.getAvatar_url())
                .totalRepos(repos.size())
                .mostUsedLanguage(topLanguage)
                .languageDistribution(languageDist)
                .followers(user.getFollowers())
                .totalStars(totalStars)
                .totalForks(totalForks)
                .mostStarredRepo(mostStarredRepo)
                .lastActiveDate(lastActiveDate)
                .accountCreatedAt(user.getCreated_at())
                .accountAgeYears(accountAgeYears)
                .repoGrowthRate(repoGrowthRate)
                .recentlyActiveRepos(recentlyActiveRepos)
                .activityScore(activityScore)
                .consistency(calculateConsistency(repos))
                .developerTier(developerTier)
                .lastFetched(Instant.now())
                .build();
    }

    // ── helpers ────────────────────────────────────────────

    private Map<String, Long> computeLanguageDistribution(List<GitHubRepoDTO> repos) {
        return repos.stream()
                .map(GitHubRepoDTO::getLanguage)
                .filter(lang -> lang != null && !lang.isBlank())
                .collect(Collectors.groupingBy(lang -> lang, Collectors.counting()));
    }

    private String findTopLanguage(Map<String, Long> languageDist) {
        return languageDist.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }

    private String findMostStarredRepo(List<GitHubRepoDTO> repos) {
        return repos.stream()
                .max(Comparator.comparingInt(GitHubRepoDTO::getStargazers_count))
                .map(GitHubRepoDTO::getName)
                .orElse("None");
    }

    private String findLastActiveDate(List<GitHubRepoDTO> repos) {
        return repos.stream()
                .map(GitHubRepoDTO::getPushed_at)
                .filter(date -> date != null && !date.isBlank())
                .max(Comparator.naturalOrder())   // ISO strings sort lexicographically
                .orElse("Unknown");
    }

    private int countRecentlyActiveRepos(List<GitHubRepoDTO> repos) {
        Instant cutoff = Instant.now().minus(Duration.ofDays(30));
        return (int) repos.stream()
                .map(GitHubRepoDTO::getPushed_at)
                .filter(date -> date != null && !date.isBlank())
                .filter(date -> Instant.parse(date).isAfter(cutoff))
                .count();
    }

    private int calculateAccountAge(String createdAt) {
        if (createdAt == null || createdAt.isBlank()) return 0;
        return (int) ChronoUnit.YEARS.between(
                Instant.parse(createdAt), Instant.now()
        );
    }

    private int calculateActivityScore(int repoCount, int totalStars,
                                       int recentlyActive, int languageCount) {
        return (repoCount * 2)
                + (totalStars * 5)
                + (recentlyActive * 10)
                + (languageCount * 8);
    }

    private String computeDeveloperTier(int score) {
        if (score >= 300) return "Power Dev";
        if (score >= 100) return "Active";
        return "Hobbyist";
    }

    private String calculateConsistency(List<GitHubRepoDTO> repos) {
        int repoCount = repos.size();
        return repoCount > 50 ? "HIGH" : repoCount > 20 ? "MEDIUM" : "LOW";
    }

    public Mono<Map<String, Long>> getLanguageDistribution(String username) {
        return repository.findById(username)
                .map(DevPulse::getLanguageDistribution)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("User not found: " + username)
                ));
    }
}