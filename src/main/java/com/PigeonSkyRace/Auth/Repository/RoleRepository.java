package com.PigeonSkyRace.Auth.Repository;

import com.PigeonSkyRace.Auth.Entity.Role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
