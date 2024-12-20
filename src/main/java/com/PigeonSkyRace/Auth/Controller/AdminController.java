package com.PigeonSkyRace.Auth.Controller;

import com.PigeonSkyRace.Auth.Entity.Role.Role;
import com.PigeonSkyRace.Auth.Entity.User.Breeder;
import com.PigeonSkyRace.Auth.Repository.BreederRepository;
import com.PigeonSkyRace.Auth.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private BreederRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("AssignRole/{Userid}")
    public String assignRole(@RequestParam("roleName") String roleName, @PathVariable Long Userid) {

        Breeder user = userRepository.findById(Userid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));


        Set<Role> roles = user.getRoles();
        if (roles == null) {
            roles = new HashSet<>();
        }


        if (!roles.contains(role)) {
            roles.add(role);
            user.setRoles(roles);


            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User already has this role");
        }
        return "Role Assigned";
    }
}
