package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Service.CompetitionService;
import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Api/Competition")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping("/")
    public List<Competition> getCompetition() {
        return competitionService.fetchCompetition();
    }

    @PostMapping("/")
    public String addCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) {
        // Call the service to add the competition
      competitionService.addCompetition(competitionDTO);
        return "Competition added successfully!" + competitionDTO.toString();
    }
}
