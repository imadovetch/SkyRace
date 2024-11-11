package com.PigeonSkyRace.Auth.repository;

import com.PigeonSkyRace.Auth.models.CompetitionPigeon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CompetitionPigeonRepository extends MongoRepository<CompetitionPigeon, String> {
    List<CompetitionPigeon> findByCompetitionId(String competitionId);

    Optional<CompetitionPigeon> findByPigeonRingNumberAndPigeonBreederId(String ringNumber, String breederId);

}

