package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Service.PigeonService;
import com.PigeonSkyRace.Auth.models.Pigeon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pigeons")
public class PigeonController {

    @Autowired
    private PigeonService pigeonService;

    // Add a new pigeon
    @PostMapping
    public Pigeon addPigeon(@RequestBody Pigeon pigeon) {
        return pigeonService.addPigeon(pigeon);
    }

    // Get all pigeons
    @GetMapping
    public List<Pigeon> getAllPigeons() {
        return pigeonService.getAllPigeons();
    }

    // Get a specific pigeon by ring number
    @GetMapping("/{ringNumber}")
    public Optional<Pigeon> getPigeonByRingNumber(@PathVariable String ringNumber) {
        return pigeonService.getPigeonByRingNumber(ringNumber);
    }

    // Delete a pigeon by ring number
    @DeleteMapping("/{ringNumber}")
    public void deletePigeon(@PathVariable String ringNumber) {
        pigeonService.deletePigeon(ringNumber);
    }
}
