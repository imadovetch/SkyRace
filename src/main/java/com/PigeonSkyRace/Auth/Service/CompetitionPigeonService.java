package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import com.PigeonSkyRace.Auth.models.CompetitionPigeon;
import com.PigeonSkyRace.Auth.models.Pigeon;
import com.PigeonSkyRace.Auth.repository.CompetitionPigeonRepository;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        double  LatitudeCompetition =  competition.getLatitude();
        double  LongitudeCompetition =  competition.getLongitude();

        double  LatitudeBreeder =  pigeon.getBreeder().getLatitude();
        double  LongitudeBreeder =  pigeon.getBreeder().getLongitude();

        double distance = calculateDistance(LatitudeCompetition, LongitudeCompetition, LatitudeBreeder, LongitudeBreeder);
        System.out.println("distance = " + distance);
        competitionPigeon.setDistance(distance);

        competitionPigeonRepository.save(competitionPigeon);
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Résultat en kilomètres
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


    // Method to update EndTime
    public String updateEndTime(String breederId, String ringNumber, LocalTime endTime) {
        CompetitionPigeon competitionPigeonOpt = competitionPigeonRepository.findByPigeon_RingNumber(ringNumber);
        System.out.println("Found CompetitionPigeons: " + competitionPigeonOpt);
     String  BreederID =  competitionPigeonOpt.getPigeon().getBreeder().getId() ;
        if (competitionPigeonOpt.getEndTime() != null) {
            throw new IllegalArgumentException("Pigeon Already done");
        }
        if (competitionPigeonOpt != null && BreederID.equals(breederId)) {
            competitionPigeonOpt.setEndTime(endTime);
            competitionPigeonRepository.save(competitionPigeonOpt);
            return "cool Pigeon done";
        }else {
            throw new IllegalArgumentException("Pigeon not found");
        }


    }


    public void EndCompetition(String competitionId) {


        List<CompetitionPigeon> competitionPigeons = competitionPigeonRepository.findByCompetitionId(competitionId);
        for (CompetitionPigeon competitionPigeon : competitionPigeons) {

            if(competitionPigeon.getEndTime() != null && competitionPigeon.getDistance() != null) {
           LocalTime   endTime = competitionPigeon.getEndTime() ;
           LocalDateTime  departTime = competitionPigeon.getCompetition().getDepartureTime();

                // Calculate Duration between departure time and end time
                Duration duration = Duration.between(departTime, departTime.with(endTime));

                double hours = duration.toHours();
                double minutes = duration.toMinutes() % 60;
                double seconds = duration.getSeconds() % 60;
                double  distanceKm = competitionPigeon.getDistance() ;

                // Convert time to total minutes
                double totalMinutes = (hours * 60) + minutes + (seconds / 60.0);

// Convert distance to meters
                double distanceMeters = distanceKm * 1000;

// Calculate speed in meters per minute
                double vitesse = distanceMeters / totalMinutes;

                System.out.println("Vitesse (m/min): " + vitesse);
            }

        }



        int  TotalPigeon =  getCountPigeonToCompetition(competitionId);

        int  PigeonCount = calculatePigeonCount( competitionId ,  TotalPigeon) ;

     //   competitionService.updateCompetition(competitionId , latitude , longitude , TotalPigeon ,PigeonCount) ;


    }



}


