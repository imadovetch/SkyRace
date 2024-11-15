package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Breeder;
import com.PigeonSkyRace.Auth.repository.BreederRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BreederServiceTest {

    @Mock
    private BreederRepository breederRepository;

    @InjectMocks
    private BreederService breederService;

    private Breeder breeder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a breeder mock
        breeder = new Breeder();
        breeder.setId("123");
        breeder.setNomColombie("johnDoe123");
        breeder.setPassword("password123");
        breeder.setRole("ROLE_BREEDER");
    }


    @Test
    public void testLoadUserByUsername_userNotFound() {
        // Given
        when(breederRepository.findByNomColombie("nonExistentUser")).thenReturn(null);

        // When / Then
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            breederService.loadUserByUsername("nonExistentUser");
        });

        assertEquals("User not found with nomColombie: nonExistentUser", exception.getMessage());
    }

    @Test
    public void testFindByNomColombie_success() {
        // Given
        when(breederRepository.findByNomColombie("johnDoe123")).thenReturn(breeder);

        // When
        Breeder result = breederService.findByNomColombie("johnDoe123");

        // Then
        assertNotNull(result);
        assertEquals("johnDoe123", result.getNomColombie());
    }

    @Test
    public void testFindByNomColombie_notFound() {
        // Given
        when(breederRepository.findByNomColombie("nonExistentUser")).thenReturn(null);

        // When
        Breeder result = breederService.findByNomColombie("nonExistentUser");

        // Then
        assertNull(result);
    }

    @Test
    public void testFindByID_success() {
        // Given
        when(breederRepository.findById("123")).thenReturn(Optional.of(breeder));

        // When
        Optional<Breeder> result = breederService.findByID("123");

        // Then
        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    public void testFindByID_notFound() {
        // Given
        when(breederRepository.findById("999")).thenReturn(Optional.empty());

        // When
        Optional<Breeder> result = breederService.findByID("999");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindAll_success() {
        // Given
        when(breederRepository.findAll()).thenReturn(List.of(breeder));

        // When
        List<Breeder> result = breederService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getId());
    }
}
