package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import com.PigeonSkyRace.Auth.models.CompetitionPigeon;
import com.PigeonSkyRace.Auth.models.Pigeon;
import com.PigeonSkyRace.Auth.repository.CompetitionPigeonRepository;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CompetitionPigeonService {

    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private PigeonService pigeonService;

    @Autowired
    private CompetitionPigeonRepository competitionPigeonRepository;

    @Autowired
    private CompetitionService competitionService;



    public void addPigeonToCompetition( CompetitionPigeon competitionPigeon) {
        Competition competition = competitionRepository.findById(competitionPigeon.getCompetition().getId()).orElseThrow();
        Pigeon pigeon = pigeonService.findById(competitionPigeon.getPigeon().getRingNumber());

        competitionPigeon.setCompetition(competition);
        competitionPigeon.setPigeon(pigeon);

        competitionPigeonRepository.save(competitionPigeon);
    }

    public List<CompetitionPigeon> getAllPigeonEtCompetition() {
        return competitionPigeonRepository.findAll();
    }

    public int getCountPigeonToCompetition(String competitionId) {
        List<CompetitionPigeon> competitionPigeons = competitionPigeonRepository.findByCompetitionId(competitionId);


        Set<String> uniquePigeons = new HashSet<>();

        for (CompetitionPigeon competitionPigeon : competitionPigeons) {

            uniquePigeons.add(competitionPigeon.getPigeon().getRingNumber());
        }

        return uniquePigeons.size();
    }


    public void StartCompetition(String competitionId) {
        double totalLat = 0;
        double totalLon = 0;
        int count = 0;

        List<CompetitionPigeon> competitionPigeons = competitionPigeonRepository.findByCompetitionId(competitionId);
        for (CompetitionPigeon competition : competitionPigeons) {
            double latitude = competition.getPigeon().getBreeder().getLatitude();
            double longitude = competition.getPigeon().getBreeder().getLongitude();
            totalLat += latitude;
            totalLon += longitude;
            count++;
        }

        double latitude = totalLat / count;
        double longitude = totalLon / count;

        int  TotalPigeon =  getCountPigeonToCompetition(competitionId);

        int  PigeonCount = calculatePigeonCount( competitionId ,  TotalPigeon) ;

        competitionService.updateCompetition(competitionId , latitude , longitude , TotalPigeon ,PigeonCount) ;


    }

    public int calculatePigeonCount(String competitionId, int totalPigeon) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));
        int percentage = competition.getPercentage();

        if (percentage <= 0) {
            throw new IllegalArgumentException("Percentage must be greater than 0");
        }

        return (int) Math.ceil((totalPigeon * percentage) / 100.0);
    }

    public Optional<CompetitionPigeon> findCompetitionPigeonByBreederIdAndRingNumber(String breederId, String ringNumber) {
        return competitionPigeonRepository.findByPigeonRingNumberAndPigeonBreederId(ringNumber, breederId);
    }

    // Method to update EndTime
    public Optional<CompetitionPigeon> updateEndTime(String breederId, String ringNumber, LocalDateTime endTime) {
        Optional<CompetitionPigeon> competitionPigeonOpt = findCompetitionPigeonByBreederIdAndRingNumber(breederId, ringNumber);
        System.out.println("Found CompetitionPigeons: " + competitionPigeonOpt);

        if (competitionPigeonOpt.isPresent()) {
            CompetitionPigeon competitionPigeon = competitionPigeonOpt.get();
            competitionPigeon.setEndTime(endTime);
            competitionPigeonRepository.save(competitionPigeon);
            return Optional.of(competitionPigeon);
        }

        return Optional.empty();
    }



}


