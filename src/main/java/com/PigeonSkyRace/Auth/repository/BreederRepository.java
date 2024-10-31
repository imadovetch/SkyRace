package com.PigeonSkyRace.Auth.repository;

import com.PigeonSkyRace.Auth.models.Breeder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BreederRepository extends MongoRepository<Breeder, String> {
 // Breeder findByUsername(String username);
  Breeder findByNomColombie(String nomColombie);
  List<Breeder> findByUsername(String username);

  Boolean existsByUsername(String username);
 Optional<Breeder> findById(String id);

}
