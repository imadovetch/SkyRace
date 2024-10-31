package com.PigeonSkyRace.Auth.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterDto {

    @NotEmpty
    private String username;
    @NotBlank
    @Size(max = 20)
    private String nomColombie;

    private String phone;
    private String address;
    @NotEmpty
    @Size(min = 6, message = "Minimum Password length is 6 characters")
    private String password;

    public @NotBlank @Size(max = 20) String getNomColombie() {
        return nomColombie;
    }

    public void setNomColombie(@NotBlank @Size(max = 20) String nomColombie) {
        this.nomColombie = nomColombie;
    }

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public @NotEmpty @Size(min = 6, message = "Minimum Password length is 6 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty @Size(min = 6, message = "Minimum Password length is 6 characters") String password) {
        this.password = password;
    }
}
