package com.PigeonSkyRace.Auth.Entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "competitions") // Maps to a SQL table
@Getter
@Setter
@ToString
@NoArgsConstructor // Lombok generates a no-args constructor
@AllArgsConstructor // Lombok generates a constructor with all fields
@Builder // Lombok generates a builder for easier object creation
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto-generated ID for the competition

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name; // Name of the competition

    private double latitude;
    private double longitude;

    @NotEmpty
    @JsonFormat(pattern = "yy/MM/dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime departureTime;

    private int pigeonTotal;

    @NotBlank
    @Column(nullable = false)
    private int percentage;

    private int pigeonCount;

    private boolean status;

    private boolean started;

    private Double distance;

    @ManyToMany
    @JoinTable(
            name = "competition_pigeon",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "pigeon_id")
    )
    private Set<CompetitionPigeon> pigeons;

    // Getters, Setters, and other methods are automatically handled by Lombok
}
