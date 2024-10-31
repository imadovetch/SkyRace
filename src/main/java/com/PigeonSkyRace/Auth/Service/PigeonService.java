package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Breeder;
import com.PigeonSkyRace.Auth.models.Pigeon;
import com.PigeonSkyRace.Auth.repository.BreederRepository;
import com.PigeonSkyRace.Auth.repository.PigeonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PigeonService {

    @Autowired
    private PigeonRepository pigeonRepository;

    @Autowired
    private BreederRepository breederRepository;

    // Add a new pigeon
    public Pigeon addPigeon(String breederId, Pigeon pigeon) {
        // Find the breeder by ID
        Breeder breeder = breederRepository.findById(breederId)
                .orElseThrow(() -> new RuntimeException("Breeder not found"));

        System.out.println("breederId: " + breederId);


        // Set the breeder in the Pigeon object directly
        pigeon.setBreeder(breeder);  // Assuming 'setBreeder' method expects a Breeder object, not an Optional.

        // Save the pigeon to the repository
        return pigeonRepository.save(pigeon);
    }

    // Get all pigeons
    public List<Pigeon> getAllPigeons() {
        return pigeonRepository.findAll();
    }

    // Find pigeon by ring number
    public Optional<Pigeon> getPigeonByRingNumber(String ringNumber) {
        return pigeonRepository.findById(ringNumber);
    }

    // Delete a pigeon by ring number
    public void deletePigeon(String ringNumber) {
        pigeonRepository.deleteById(ringNumber);
    }

    public List<Pigeon> findByBreederId(String breederId) {
        return pigeonRepository.findByBreederId(breederId);
    }

}

