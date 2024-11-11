package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Service.CompetitionPigeonService;
import com.PigeonSkyRace.Auth.Service.CompetitionService;
import com.PigeonSkyRace.Auth.models.CompetitionPigeon;
import com.PigeonSkyRace.Auth.models.EndTimeRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Api/CompetitionPigeon")
public class CompetitionPigeonController {


    @Autowired
    private CompetitionPigeonService competitionPigeonService;

    private static final Logger logger = LoggerFactory.getLogger(CompetitionPigeonController.class);

    @PostMapping("/AddPigeonToCompetition")
    public String addPigeonToCompetition(@Valid @RequestBody CompetitionPigeon competitionPigeon) {
        logger.info("Received request to add pigeon to competition");

        competitionPigeonService.addPigeonToCompetition(
                competitionPigeon
        );

        logger.info("Pigeon added to competition successfully");
        return "Competition added successfully!";
    }

    @GetMapping("/")
    public List<CompetitionPigeon> getAllPigeonEtCompetition() {
        return competitionPigeonService.getAllPigeonEtCompetition();
    }



    @GetMapping("/{competitionId}/Start-competition")
    public String StartCompetition(@PathVariable String competitionId) {
         competitionPigeonService.StartCompetition(competitionId);

          return "Competition Start!";
    }


    @PostMapping("/pigeon/{ringNumber}/breeder/{breederId}/end-time")
    public ResponseEntity<CompetitionPigeon> updateEndTime(
            @PathVariable String ringNumber,
            @PathVariable String breederId,
            @RequestBody EndTimeRequest endTimeRequest) {

        // Extract the end time
        LocalDateTime endTime = endTimeRequest.getEndTime();

        // Log the parsed end time
        System.out.println("Parsed endTime: " + endTime);

        Optional<CompetitionPigeon> updatedCompetitionPigeon = competitionPigeonService.updateEndTime(breederId, ringNumber, endTime);

        return updatedCompetitionPigeon
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }




}
