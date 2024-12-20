package com.PigeonSkyRace.Auth.Test.Service;

import com.PigeonSkyRace.Auth.Service.PigeonService;
import com.PigeonSkyRace.Auth.models.Breeder;
import com.PigeonSkyRace.Auth.models.Pigeon;
import com.PigeonSkyRace.Auth.repository.BreederRepository;
import com.PigeonSkyRace.Auth.repository.PigeonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PigeonServiceTest {

    @Mock
    private PigeonRepository pigeonRepository;

    @Mock
    private BreederRepository breederRepository;

    @InjectMocks
    private PigeonService pigeonService;

    private Breeder breeder;
    private Pigeon pigeon;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a breeder mock
        breeder = new Breeder();
        breeder.setId("123");
        breeder.setNomColombie("John Doe");

        // Set up a pigeon mock
        pigeon = new Pigeon();
        pigeon.setRingNumber("ABC123");
        pigeon.setColor("Pigeon One");
        pigeon.setBreeder(breeder);
    }

    @Test
    public void testAddPigeon_success() {
        // Given
        when(breederRepository.findById("123")).thenReturn(Optional.of(breeder));
        when(pigeonRepository.existsById("ABC123")).thenReturn(false);
        when(pigeonRepository.save(pigeon)).thenReturn(pigeon);

        // When
        Pigeon result = pigeonService.addPigeon("123", pigeon);

        // Then
        assertNotNull(result);
        assertEquals("ABC123", result.getRingNumber());
        verify(pigeonRepository).save(pigeon);
    }

    @Test
    public void testAddPigeon_breederNotFound() {
        // Given
        when(breederRepository.findById("123")).thenReturn(Optional.empty());

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pigeonService.addPigeon("123", pigeon);
        });

        assertEquals("Breeder not found", exception.getMessage());
    }

    @Test
    public void testAddPigeon_pigeonAlreadyUsed() {
        // Given
        when(breederRepository.findById("123")).thenReturn(Optional.of(breeder));
        when(pigeonRepository.existsById("ABC123")).thenReturn(true);

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pigeonService.addPigeon("123", pigeon);
        });

        assertEquals("Pigeon already used", exception.getMessage());
    }

    @Test
    public void testGetAllPigeons() {
        // Given
        when(pigeonRepository.findAll()).thenReturn(List.of(pigeon));

        // When
        var result = pigeonService.getAllPigeons();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("ABC123", result.get(0).getRingNumber());
    }

    @Test
    public void testGetPigeonByRingNumber_success() {
        // Given
        when(pigeonRepository.findById("ABC123")).thenReturn(Optional.of(pigeon));

        // When
        Optional<Pigeon> result = pigeonService.getPigeonByRingNumber("ABC123");

        // Then
        assertTrue(result.isPresent());
        assertEquals("ABC123", result.get().getRingNumber());
    }

    @Test
    public void testGetPigeonByRingNumber_notFound() {
        // Given
        when(pigeonRepository.findById("ABC123")).thenReturn(Optional.empty());

        // When
        Optional<Pigeon> result = pigeonService.getPigeonByRingNumber("ABC123");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    public void testDeletePigeon() {
        // Given
        doNothing().when(pigeonRepository).deleteById("ABC123");

        // When
        pigeonService.deletePigeon("ABC123");

        // Then
        verify(pigeonRepository).deleteById("ABC123");
    }

    @Test
    public void testFindByBreederId() {
        // Given
        when(pigeonRepository.findByBreederId("123")).thenReturn(List.of(pigeon));

        // When
        var result = pigeonService.findByBreederId("123");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ABC123", result.get(0).getRingNumber());
    }

    @Test
    public void testFindById() {
        // Given
        when(pigeonRepository.findById("ABC123")).thenReturn(Optional.of(pigeon));

        // When
        Pigeon result = pigeonService.findById("ABC123");

        // Then
        assertNotNull(result);
        assertEquals("ABC123", result.getRingNumber());
    }
}
