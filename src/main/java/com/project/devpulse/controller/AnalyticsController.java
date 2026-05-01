package com.project.devpulse.controller;



import com.project.devpulse.dto.DevPulseResponseDTO;
import com.project.devpulse.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{username}")
    public Mono<DevPulseResponseDTO> getAnalytics(@PathVariable String username) {
        return analyticsService.buildDevPulse(username);
    }
}