package com.project.devpulse.controller;

import com.project.devpulse.model.DevPulse;
import com.project.devpulse.service.DevPulseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/devpulse")
@RequiredArgsConstructor
public class DevPulseController {

    private final DevPulseService service;

    @GetMapping("/{username}")
    public Mono<DevPulse> getUser(@PathVariable String username) {
        return service.getUser(username);
    }

    @PostMapping
    public Mono<DevPulse> save(@RequestBody DevPulse devPulse) {
        return service.saveUser(devPulse);
    }
}