package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Service.CompetitionPigeonService;
import com.PigeonSkyRace.Auth.Service.CompetitionService;
import com.PigeonSkyRace.Auth.Service.TokenService;
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
import java.time.LocalTime;
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

    @Autowired
    private TokenService tokenService ;

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


    @PostMapping("/pigeon/{ringNumber}/end-time")
    public String updateEndTime(
            @PathVariable String ringNumber,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody EndTimeRequest endTimeRequest) {

        LocalDateTime endTime = endTimeRequest.getEndTime();

        LocalTime endTimePlusOneHour = endTime.plusHours(1).toLocalTime();


        String breederId = tokenService.getBreederIdFromToken(authorizationHeader);

        return competitionPigeonService.updateEndTime(breederId, ringNumber, endTimePlusOneHour);
    }


    @GetMapping("/{competitionId}/End-competition")
    public String EndCompetition(@PathVariable String competitionId) {
        competitionPigeonService.StartCompetition(competitionId);

        return "Competition Start!";
    }





}
