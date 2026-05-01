package com.project.devpulse.repository;

import com.project.devpulse.model.DevPulse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DevPulseRepository extends ReactiveMongoRepository<DevPulse,String> {

}
