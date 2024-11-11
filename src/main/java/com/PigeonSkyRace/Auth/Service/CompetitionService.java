package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import com.PigeonSkyRace.Auth.repository.CompetitionPigeonRepository;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private PigeonService pigeonService;

    @Autowired
    private CompetitionPigeonRepository competitionPigeonRepository;



    // Method to add a new competition
    public Competition addCompetition(CompetitionDTO competitionDTO) {
        // Convert CompetitionDTO to Competition entity
        Competition competition = new Competition();
        competition.setName(competitionDTO.getName());
        competition.setDepartureTime(competitionDTO.getDepartureTime());
        competition.setPercentage(competitionDTO.getPercentage());
        competition.setStatus(true);
        competition.setStarted(false);

        // Save the competition to the database
        competitionRepository.save(competition);
        return competition ;
    }
    public List<Competition> fetchCompetition() {
        return competitionRepository.findAll();
    }


    public void updateCompetition(String competitionId, double latitude , double longitude , int TotalPigeon , int PigeonCount) {

        Competition existingCompetition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        existingCompetition.setPigeonTotal(TotalPigeon);
        existingCompetition.setPigeonCount(PigeonCount);
        existingCompetition.setLatitude(latitude);
        existingCompetition.setLongitude(longitude);
        existingCompetition.setStarted(true);

        competitionRepository.save(existingCompetition);
        System.out.println("competitionRepository.save(existingCompetition)" + competitionRepository.save(existingCompetition));
    }

    public void updateCompetition(Competition competition) {
        competitionRepository.save(competition);
    }


}


