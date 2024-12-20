package com.PigeonSkyRace.Auth.Entity.model;

import com.PigeonSkyRace.Auth.Entity.User.Breeder;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "pigeons")
@Getter
@Setter
@ToString
@NoArgsConstructor // Lombok generates a no-args constructor
@AllArgsConstructor // Lombok generates a constructor with all fields
@Builder // Optional: Lombok generates a builder pattern for easy object creation
public class Pigeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Auto-generated ID for the primary key

    @Column(nullable = false, unique = true)
    private Long ringNumber; // Unique ring number for each pigeon

    @Column(nullable = false)
    private String gender;

    private int age;

    private String color;

    @ManyToOne
    @JoinColumn(name = "breeder_id") // Foreign key to Breeder entity
    private Breeder breeder;

    @ManyToMany
    @JoinTable(
            name = "competition_pigeon",
            joinColumns = @JoinColumn(name = "pigeon_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<CompetitionPigeon> competitions;

    // The constructors are generated by Lombok
    // No need for additional constructor code since Lombok handles it
}
