package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import com.PigeonSkyRace.Auth.models.Pigeon;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import com.PigeonSkyRace.Auth.repository.PigeonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private PigeonRepository pigeonRepository;

    // Method to add a new competition
    public Competition addCompetition(CompetitionDTO competitionDTO) {
        // Convert CompetitionDTO to Competition entity
        Competition competition = new Competition();
        competition.setName(competitionDTO.getName());
        competition.setDepartureTime(competitionDTO.getDepartureTime());
        competition.setDistance(competitionDTO.getDistance());
        competition.setPigeonCount(competitionDTO.getPigeonCount());
        competition.setPercentage(competitionDTO.getPercentage());
        competition.setStatus(true);
        competition.setPigeons(competitionDTO.getPigeons());

        // Save each pigeon and add to the savedPigeons list
        for (Pigeon pigeon : competitionDTO.getPigeons()) {
            pigeonRepository.save(pigeon); // Save pigeon
             // Add to list of saved pigeons
        }
        // Save the competition to the database
        return competitionRepository.save(competition);
    }
    public List<Competition> fetchCompetition() {
        return competitionRepository.findAll();
    }
}
