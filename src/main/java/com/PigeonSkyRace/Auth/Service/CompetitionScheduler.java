package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@Service
public class CompetitionScheduler {

    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private CompetitionPigeonService competitionPigeonService;

    @Scheduled(fixedRate = 60000)
    public void checkCompetitionsToStart() {
        List<Competition> competitions = competitionService.fetchCompetition();
        LocalDateTime now = LocalDateTime.now();
        System.out.println("ChronoLocalDate.from(now)" + ChronoLocalDate.from(now));

        for (Competition competition : competitions) {
            System.out.println("competition.getDepartureTime()" + competition.getDepartureTime());
            if (competition.getDepartureTime().isBefore(now) && !competition.isStarted() || competition.getDepartureTime().equals(ChronoLocalDate.from(now)) && !competition.isStarted()) {
                competitionPigeonService.StartCompetition(competition.getId());

                System.out.println("Competition " + competition.getName() + " has started!");
            }
        }
    }
}

