package com.PigeonSkyRace.Auth.Service;



import com.PigeonSkyRace.Auth.models.*;
import com.PigeonSkyRace.Auth.repository.CompetitionPigeonRepository;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompetitionPigeonServiceTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private PigeonService pigeonService;

    @Mock
    private CompetitionPigeonRepository competitionPigeonRepository;

    @Mock
    private CompetitionService competitionService;

    @InjectMocks
    private CompetitionPigeonService competitionPigeonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPigeonToCompetition() {

        Competition competition = new Competition();
        competition.setId("competition1");

        Pigeon pigeon = new Pigeon();
        pigeon.setRingNumber("ring123");

        CompetitionPigeon competitionPigeon = new CompetitionPigeon();
        competitionPigeon.setCompetition(competition);
        competitionPigeon.setPigeon(pigeon);

        when(competitionRepository.findById("competition1")).thenReturn(Optional.of(competition));
        when(pigeonService.findById("ring123")).thenReturn(pigeon);


        competitionPigeonService.addPigeonToCompetition(competitionPigeon);


        verify(competitionRepository, times(1)).findById("competition1");
        verify(pigeonService, times(1)).findById("ring123");
        verify(competitionPigeonRepository, times(1)).save(competitionPigeon);
    }

    @Test
    void testCalculateDistance() {

        double distance = CompetitionPigeonService.calculateDistance(34.0522, -118.2437, 36.7783, -119.4179);
        assertTrue(distance > 300); // Assert approximate distance in km
    }

    @Test
    void testGetCountPigeonToCompetition() {
        CompetitionPigeon competitionPigeon1 = new CompetitionPigeon();
        Pigeon pigeon1 = new Pigeon();
        pigeon1.setRingNumber("ring123");
        competitionPigeon1.setPigeon(pigeon1);

        CompetitionPigeon competitionPigeon2 = new CompetitionPigeon();
        Pigeon pigeon2 = new Pigeon();
        pigeon2.setRingNumber("ring124");
        competitionPigeon2.setPigeon(pigeon2);

        List<CompetitionPigeon> competitionPigeons = List.of(competitionPigeon1, competitionPigeon2);

        when(competitionPigeonRepository.findByCompetitionId("competition1")).thenReturn(competitionPigeons);


        int count = competitionPigeonService.getCountPigeonToCompetition("competition1");
        assertEquals(2, count);
    }

    @Test
    void testCalculatePigeonCount() {
        // Mock data
        Competition competition = new Competition();
        competition.setPercentage(50);

        when(competitionRepository.findById("competition1")).thenReturn(Optional.of(competition));

        // Test the method
        int pigeonCount = competitionPigeonService.calculatePigeonCount("competition1", 10);
        assertEquals(5, pigeonCount); // Assert 50% of 10
    }

    @Test
    void testFindOptimalReleasePoint() {

        List<CompetitionPigeon> pigeons = new ArrayList<>();
        Pigeon pigeon1 = new Pigeon();
        Breeder breeder1 = new Breeder();
        breeder1.setLatitude(34.0522);
        breeder1.setLongitude(-118.2437);
        pigeon1.setBreeder(breeder1);

        CompetitionPigeon competitionPigeon1 = new CompetitionPigeon();
        competitionPigeon1.setPigeon(pigeon1);
        pigeons.add(competitionPigeon1);

        double[] releasePoint = CompetitionPigeonService.findOptimalReleasePoint(pigeons, 100.0);
        assertNotNull(releasePoint);
    }

    @Test
    void testUpdateEndTime() {

        Breeder breeder = new Breeder();
        breeder.setId("breeder1");

        Pigeon pigeon = new Pigeon();
        pigeon.setRingNumber("ring123");
        pigeon.setBreeder(breeder);

        CompetitionPigeon competitionPigeon = new CompetitionPigeon();
        competitionPigeon.setPigeon(pigeon);

        when(competitionPigeonRepository.findByPigeon_RingNumber("ring123")).thenReturn(competitionPigeon);

        String result = competitionPigeonService.updateEndTime("breeder1", "ring123", LocalTime.now());
        assertEquals("cool Pigeon done", result);

        verify(competitionPigeonRepository, times(1)).save(competitionPigeon);
    }
}

