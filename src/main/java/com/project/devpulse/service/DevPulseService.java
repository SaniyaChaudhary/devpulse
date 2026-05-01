package com.project.devpulse.service;
import com.project.devpulse.model.DevPulse;
import com.project.devpulse.repository.DevPulseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DevPulseService {

    private final DevPulseRepository repository;

    public Mono<DevPulse> getUser(String username) {
        return repository.findById(username);
    }

    public Mono<DevPulse> saveUser(DevPulse devPulse) {
        return repository.save(devPulse);
    }
}