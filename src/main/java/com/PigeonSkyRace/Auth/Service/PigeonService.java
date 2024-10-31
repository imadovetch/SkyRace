package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Pigeon;
import com.PigeonSkyRace.Auth.repository.PigeonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PigeonService {

    @Autowired
    private PigeonRepository pigeonRepository;

    // Add a new pigeon
    public Pigeon addPigeon(Pigeon pigeon) {
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
}

