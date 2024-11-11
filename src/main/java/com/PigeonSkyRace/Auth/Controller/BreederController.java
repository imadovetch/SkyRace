package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Service.BreederService;
import com.PigeonSkyRace.Auth.Service.PigeonService;
import com.PigeonSkyRace.Auth.models.Breeder;
import com.PigeonSkyRace.Auth.models.Pigeon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Api/breeders")
public class BreederController {


    @Autowired
    BreederService breederService ;

    @Autowired
    PigeonService pigeonService ;

    @GetMapping("/")
    public ResponseEntity<List<Breeder>> getAllBreeders() {
        List<Breeder> breeders = breederService.findAll();

        for (Breeder b : breeders) {
            List<Pigeon> pigeons = pigeonService.findByBreederId(b.getId());
            for (Pigeon p : pigeons) {
                p.setBreeder(null);
            }
            b.setPigeons(pigeons);
        }

        return ResponseEntity.ok(breeders);
    }



    @GetMapping("/{breederId}")
    public ResponseEntity<Breeder> getBreederById(@PathVariable String breederId) {
        Optional<Breeder> breeder = breederService.findByID(breederId);


        return breeder.map(b -> {

            List<Pigeon> pigeons = pigeonService.findByBreederId(breederId);
            for (Pigeon p : pigeons) {
                p.setBreeder(null);
            }
            b.setPigeons(pigeons);
            return ResponseEntity.ok(b);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }





}
