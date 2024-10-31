package com.PigeonSkyRace.Auth.repository;

import com.PigeonSkyRace.Auth.models.Pigeon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PigeonRepository extends MongoRepository<Pigeon, String> {

    List<Pigeon> findByBreederId(String breederId);

}

