package com.PigeonSkyRace.Auth.StartUp;

import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.Pigeon;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import com.PigeonSkyRace.Auth.repository.PigeonRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class StartUp implements CommandLineRunner {

    @Autowired
    private PigeonRepository pigeonRepository;
    @Autowired
    private CompetitionRepository competitionRepository;
    @Override
    public void run(String... args) throws Exception {


    }
}
