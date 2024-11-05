package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Breeder;
import com.PigeonSkyRace.Auth.models.Pigeon;
import com.PigeonSkyRace.Auth.repository.BreederRepository;
import com.PigeonSkyRace.Auth.repository.PigeonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        if (pigeonRepository.existsById(pigeon.getRingNumber())) {
            throw new RuntimeException("Pigeon already used");
        }

        pigeon.setBreeder(breeder);

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

    public  Pigeon findById(String id) {
        return pigeonRepository.findById(id).orElse(null);
    }

}

