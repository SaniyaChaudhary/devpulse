package com.project.devpulse.controller;
import com.project.devpulse.dto.DevPulseResponseDTO;
import com.project.devpulse.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{username}")
    public Mono<DevPulseResponseDTO> getAnalytics(@PathVariable String username) {
        return analyticsService.buildDevPulse(username);
    }

    @GetMapping("/{username}/languages")
    public Mono<Map<String, Long>> getLanguages(@PathVariable String username) {
        return analyticsService.getLanguageDistribution(username);
    }
}