package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Service.PigeonService;
import com.PigeonSkyRace.Auth.models.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Api/pigeons")
public class PigeonController {

    @Autowired
    private PigeonService pigeonService;


    @Autowired
    private PigeonResponseDto pigeonDto ;

    // Add a new pigeon
    @PostMapping("/{breederId}/pigeons")
    public ResponseEntity<Pigeon> addPigeon(
            @PathVariable String breederId,
            @RequestBody Pigeon pigeon) {

        Pigeon savedPigeon = pigeonService.addPigeon(breederId, pigeon);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPigeon);
    }



    @GetMapping("/{pigeonId}")
    public ResponseEntity<PigeonResponseDto> getPigeonWithBreeder(@PathVariable String pigeonId) {
        Optional<Pigeon> pigeonOpt = pigeonService.getPigeonByRingNumber(pigeonId);

        if (pigeonOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pigeon pigeon = pigeonOpt.get();

        BeanUtils.copyProperties(pigeon, pigeonDto);

        Breeder breeder = pigeon.getBreeder();
        if (breeder != null) {
            BreederDto breederDto = new BreederDto();
            BeanUtils.copyProperties(breeder, breederDto);
            pigeonDto.setBreeder(breederDto);
        }

        return ResponseEntity.ok(pigeonDto);
    }



    // Get all pigeons
    @GetMapping
    public List<PigeonResponseDto> getAllPigeons() {
        List<Pigeon> pigeons = pigeonService.getAllPigeons();
        List<PigeonResponseDto> pigeonDtos = new ArrayList<>();

        System.out.println(pigeonDtos);
        for (Pigeon pigeon : pigeons) {


            BeanUtils.copyProperties(pigeon, pigeonDto);
            System.out.println("pigeonDto" + pigeon);
            Breeder breeder = pigeon.getBreeder();
            if (breeder != null) {
                BreederDto breederDto = new BreederDto();
                BeanUtils.copyProperties(breeder, breederDto);
                pigeonDto.setBreeder(breederDto);
            }

            pigeonDtos.add(pigeonDto);
        }

        return pigeonDtos;
    }

    // Delete a pigeon by ring number
    @DeleteMapping("/{ringNumber}")
    public void deletePigeon(@PathVariable String ringNumber) {
        pigeonService.deletePigeon(ringNumber);
    }
}
