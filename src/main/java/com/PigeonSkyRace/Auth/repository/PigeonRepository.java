package com.PigeonSkyRace.Auth.repository;

import com.PigeonSkyRace.Auth.models.Pigeon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PigeonRepository extends MongoRepository<Pigeon, String> {
    // Custom queries can be added here if needed, e.g., find by color or age
}

