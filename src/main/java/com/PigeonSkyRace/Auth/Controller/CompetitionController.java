package com.PigeonSkyRace.Auth.Controller;


import com.PigeonSkyRace.Auth.Entity.model.Competition;
import com.PigeonSkyRace.Auth.Entity.model.CompetitionDTO;
import com.PigeonSkyRace.Auth.Service.CompetitionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Secured("ROLE_ORGANIZER")
    public String addCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) {
        System.out.println("hi");
        return "ResponseEntity.ok(400)";
//        competitionService.addCompetition(competitionDTO);
//        return "Competition added successfully!" + competitionDTO.toString();
    }
    @GetMapping("/End-Competition/{competitionid}")
    public ResponseEntity<Object> endCompetition(@PathVariable Long competitionid) {

        String n = competitionService.endCompetition(competitionid);

        return ResponseEntity.ok(n);
    }
//    @GetMapping("GetCompetitionResult")
//    public ResponseEntity<Object> getCompetitionResult() {
//
//    }
}

