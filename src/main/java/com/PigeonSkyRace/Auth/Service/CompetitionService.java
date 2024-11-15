package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import com.PigeonSkyRace.Auth.models.CompetitionPigeon;
import com.PigeonSkyRace.Auth.repository.CompetitionPigeonRepository;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Competition getCompetitionByid(String competitionId) {
      return   competitionRepository.findById(competitionId).orElseThrow(() -> new RuntimeException("Competition not found"));
    }

    public String endCompetition(String competitionId) {

        Optional<Competition> competitionOpt = competitionRepository.findById(competitionId);

        if (competitionOpt.isPresent()) {

            Competition competition = competitionOpt.get();

            competition.setStatus(false);
            competition.setStarted(false);
            competitionRepository.save(competition);

            this.calculateResult(competitionId);
            return "Updated seccseflly";
        }

        return "No competition found";
    }

    private void calculateResult(String competitionId){

        List<CompetitionPigeon> competitionPigeons = competitionPigeonRepository.findByCompetitionId(competitionId);


        for (CompetitionPigeon competitionPigeon : competitionPigeons) {
            System.out.println(competitionPigeon);

            long totalSeconds = competitionPigeon.getEndTime().toSecondOfDay();

            if (totalSeconds != 0) {
                double vitesse = competitionPigeon.getDistance() / totalSeconds;
                competitionPigeon.setVitesse(vitesse);
            } else {
                System.out.println("Error: EndTime is zero, cannot calculate speed");
            }


        }
        competitionPigeons.sort(new Comparator<CompetitionPigeon>() {
            @Override
            public int compare(CompetitionPigeon p1, CompetitionPigeon p2) {
                return Double.compare(p2.getVitesse(), p1.getVitesse());
            }
        });

        for (int rank = 0; rank < competitionPigeons.size(); rank++) {
            CompetitionPigeon competitionPigeon = competitionPigeons.get(rank);

            double score = 100 * (1 - (double) (rank) / (competitionPigeons.size() - 1));
            competitionPigeon.setScore(score);

            competitionPigeonRepository.save(competitionPigeon);
        }


    }

}


