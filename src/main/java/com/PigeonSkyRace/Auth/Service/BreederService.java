package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Breeder;
import com.PigeonSkyRace.Auth.repository.BreederRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreederService implements UserDetailsService {

    @Autowired
    BreederRepository breederRepository;


    @Override
    public UserDetails loadUserByUsername(String nomColombie) throws UsernameNotFoundException {

        Breeder breeder = breederRepository.findByNomColombie(nomColombie);
        if (breeder != null) {

            return User.withUsername(breeder.getNomColombie())
                    .password(breeder.getPassword())
                    .roles(breeder.getRole())
                    .build();
        }

        throw new UsernameNotFoundException("User not found with nomColombie: " + nomColombie);
    }

    public  Breeder findByNomColombie(String nomColombie){
        return breederRepository.findByNomColombie(nomColombie);
    }

    public Optional<Breeder> findByID(String id){
        return breederRepository.findById(id);
    }

    public List<Breeder> findAll(){
        return breederRepository.findAll();
    }


}
