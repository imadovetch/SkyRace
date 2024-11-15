package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import com.PigeonSkyRace.Auth.models.CompetitionPigeon;
import com.PigeonSkyRace.Auth.repository.CompetitionPigeonRepository;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompetitionServiceTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private CompetitionPigeonRepository competitionPigeonRepository;

    @InjectMocks
    private CompetitionService competitionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCompetition() {
        // Arrange
        CompetitionDTO competitionDTO = new CompetitionDTO();
        competitionDTO.setName("SkyRace 2024");
        competitionDTO.setDepartureTime(LocalDateTime.parse("2024-11-20T10:00:00"));
        competitionDTO.setPercentage(50);

        Competition savedCompetition = new Competition();
        savedCompetition.setName("SkyRace 2024");
        when(competitionRepository.save(any(Competition.class))).thenReturn(savedCompetition);

        // Act
        Competition result = competitionService.addCompetition(competitionDTO);

        // Assert
        assertNotNull(result);
        assertEquals("SkyRace 2024", result.getName());
        verify(competitionRepository, times(1)).save(any(Competition.class));
    }

    @Test
    void testFetchCompetition() {
        // Arrange
        List<Competition> competitions = List.of(new Competition(), new Competition());
        when(competitionRepository.findAll()).thenReturn(competitions);

        // Act
        List<Competition> result = competitionService.fetchCompetition();

        // Assert
        assertEquals(2, result.size());
        verify(competitionRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCompetition() {
        // Arrange
        String competitionId = "12345";
        Competition competition = new Competition();
        competition.setId(competitionId);
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));

        // Act
        competitionService.updateCompetition(competitionId, 45.0, -93.0, 100, 50);

        // Assert
        assertEquals(45.0, competition.getLatitude());
        assertEquals(-93.0, competition.getLongitude());
        assertEquals(100, competition.getPigeonTotal());
        assertTrue(competition.isStarted());
        verify(competitionRepository, times(1)).save(competition);
    }

    @Test
    void testEndCompetition() {
        // Arrange
        String competitionId = "12345";
        Competition competition = new Competition();
        competition.setId(competitionId);
        when(competitionRepository.findById(competitionId)).thenReturn(Optional.of(competition));

        // Act
        String result = competitionService.endCompetition(competitionId);

        // Assert
        assertEquals("Updated seccseflly", result);
        assertFalse(competition.isStatus());
        assertFalse(competition.isStarted());
        verify(competitionRepository, times(1)).save(competition);
    }

    @Test
    void testCalculateResult() {
        // Arrange
        String competitionId = "12345";
        CompetitionPigeon pigeon1 = new CompetitionPigeon();
        pigeon1.setDistance(1000.0);
        pigeon1.setEndTime(LocalTime.of(12, 0, 0));

        CompetitionPigeon pigeon2 = new CompetitionPigeon();
        pigeon2.setDistance(800.0);
        pigeon2.setEndTime(LocalTime.of(12, 30, 0));

        when(competitionPigeonRepository.findByCompetitionId(competitionId))
                .thenReturn(List.of(pigeon1, pigeon2));

        // Act
        competitionService.endCompetition(competitionId);

        // Assert
        verify(competitionPigeonRepository, times(2)).save(any(CompetitionPigeon.class));
    }
}
