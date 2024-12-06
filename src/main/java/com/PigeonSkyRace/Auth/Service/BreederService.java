package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.Entity.User.Breeder;
import com.PigeonSkyRace.Auth.Repository.BreederRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreederService  {

    @Autowired
    BreederRepository breederRepository;




    public Breeder findByNomColombie(String nomColombie){
        return breederRepository.findByNomColombie(nomColombie);
    }

    public Optional<Breeder> findByID(Long id){
        return breederRepository.findById(id);
    }

    public List<Breeder> findAll(){
        return breederRepository.findAll();
    }


}
