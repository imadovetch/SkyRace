package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Service.CompetitionPigeonService;
import com.PigeonSkyRace.Auth.Service.CompetitionService;
import com.PigeonSkyRace.Auth.models.CompetitionPigeon;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
