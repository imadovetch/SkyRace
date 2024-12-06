package com.PigeonSkyRace.Auth.Entity.User;

import com.PigeonSkyRace.Auth.Entity.Role.Role;
import com.PigeonSkyRace.Auth.Entity.model.Pigeon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Breeder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userID ;
    private String name;
    private String username;
    private String nomColombie;
    private String password;
    private double latitude; // GPS coordinates
    private double longitude;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
     private Set<Role> roles;

     @OneToMany(fetch = FetchType.EAGER)
     private List<Pigeon> pigeons;



}
