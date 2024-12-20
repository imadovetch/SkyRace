package com.PigeonSkyRace.Auth.Entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "competition_pigeons")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionPigeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "pigeon_id", nullable = false)
    private Pigeon pigeon;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private Double distance;
    private Double vitesse;
    private Double score;

    // No need for explicit getters/setters due to Lombok annotations
}
