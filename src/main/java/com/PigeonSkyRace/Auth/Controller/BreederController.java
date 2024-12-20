package com.PigeonSkyRace.Auth.Controller;


import com.PigeonSkyRace.Auth.Entity.User.Breeder;
import com.PigeonSkyRace.Auth.Entity.model.Pigeon;
import com.PigeonSkyRace.Auth.Service.BreederService;
import com.PigeonSkyRace.Auth.Service.PigeonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Api/breeders")
@RequiredArgsConstructor
public class BreederController {



  private final BreederService breederService ;


    private final  PigeonService pigeonService ;

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
    public ResponseEntity<Breeder> getBreederById(@PathVariable Long breederId) {
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
