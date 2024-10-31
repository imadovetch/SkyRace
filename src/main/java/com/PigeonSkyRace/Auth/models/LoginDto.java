package com.PigeonSkyRace.Auth.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDto {

    @NotEmpty
    private String password;

    @NotBlank
    @Size(max = 20)
    private String nomColombie;

    public @NotBlank @Size(max = 20) String getNomColombie() {
        return nomColombie;
    }

    public void setNomColombie(@NotBlank @Size(max = 20) String nomColombie) {
        this.nomColombie = nomColombie;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
