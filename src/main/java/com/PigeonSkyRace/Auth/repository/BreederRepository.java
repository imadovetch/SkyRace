package com.PigeonSkyRace.Auth.repository;

import com.PigeonSkyRace.Auth.models.Breeder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BreederRepository extends MongoRepository<Breeder, String> {
  Breeder findByUsername(String username);
  Breeder findByNomColombie(String nomColombie);

  Boolean existsByUsername(String username);


}
