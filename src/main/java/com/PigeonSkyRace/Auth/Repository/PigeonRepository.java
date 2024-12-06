package com.PigeonSkyRace.Auth.Repository;

import com.PigeonSkyRace.Auth.Entity.model.Pigeon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PigeonRepository extends JpaRepository<Pigeon, Long> {

    // Find pigeons by breeder ID
    List<Pigeon> findByBreederId(Long breederId);  // Adjusting to use JPA's field navigation

}
