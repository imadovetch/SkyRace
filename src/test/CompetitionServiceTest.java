package com.PigeonSkyRace.Auth.Test.Service;

import com.PigeonSkyRace.Auth.Service.CompetitionService;
import com.PigeonSkyRace.Auth.Service.PigeonService;
import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import com.PigeonSkyRace.Auth.repository.CompetitionPigeonRepository;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CompetitionServiceTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private PigeonService pigeonService;

    @Mock
    private CompetitionPigeonRepository competitionPigeonRepository;

    @InjectMocks
    private CompetitionService competitionService;

    private CompetitionDTO competitionDTO;
    private Competition competition;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        competitionDTO = new CompetitionDTO();
        competitionDTO.setName("Test Competition");
        competitionDTO.setDepartureTime(LocalDateTime.now());
        competitionDTO.setPercentage(10);

        competition = new Competition();
        competition.setId("1");
        competition.setName("Test Competition");
        competition.setDepartureTime(LocalDateTime.now());
        competition.setPercentage(10);
        competition.setStatus(true);
        competition.setStarted(false);
    }

    @Test
    public void testAddCompetition() {
        // Mock the behavior of competitionRepository.save()
        when(competitionRepository.save(any(Competition.class))).thenReturn(competition);

        // Call the method under test
        Competition result = competitionService.addCompetition(competitionDTO);

        // Verify the interaction with the repository
        verify(competitionRepository, times(1)).save(any(Competition.class));

        // Assertions
        assertNotNull(result);
        assertEquals("Test Competition", result.getName());
    }

    @Test
    public void testFetchCompetition() {
        // Mock the behavior of competitionRepository.findAll()
        List<Competition> competitions = new ArrayList<>();
        competitions.add(competition);
        when(competitionRepository.findAll()).thenReturn(competitions);

        // Call the method under test
        List<Competition> result = competitionService.fetchCompetition();

        // Verify the interaction with the repository
        verify(competitionRepository, times(1)).findAll();

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Competition", result.get(0).getName());
    }



    @Test
    public void testGetCompetitionById() {
        // Mock the behavior of competitionRepository.findById()
        when(competitionRepository.findById("1")).thenReturn(Optional.of(competition));

        // Call the method under test
        Competition result = competitionService.getCompetitionByid("1");

        // Verify the interaction with the repository
        verify(competitionRepository, times(1)).findById("1");

        // Assertions
        assertNotNull(result);
        assertEquals("Test Competition", result.getName());
    }



    @Test
    public void testEndCompetition_NotFound() {
        // Mock the behavior of competitionRepository.findById() to return empty
        when(competitionRepository.findById("1")).thenReturn(Optional.empty());

        // Call the method under test
        String result = competitionService.endCompetition("1");

        // Verify the interaction with the repository
        verify(competitionRepository, times(1)).findById("1");

        // Assertions
        assertEquals("No competition found", result);
    }
}
