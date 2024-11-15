package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Service.CompetitionService;
import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Api/Competition")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping()
    public List<Competition> getCompetition() {
        return competitionService.fetchCompetition();
    }

    @PostMapping()
    public String addCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) {

        competitionService.addCompetition(competitionDTO);
        return "Competition added successfully!" + competitionDTO.toString();
    }
    @GetMapping("/End-Competition/{competitionid}")
    public ResponseEntity<Object> endCompetition(@PathVariable String competitionid) {

        String n = competitionService.endCompetition(competitionid);

        return ResponseEntity.ok(n);
    }
}
