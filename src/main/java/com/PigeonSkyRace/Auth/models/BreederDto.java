package com.PigeonSkyRace.Auth.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class BreederDto {
    @NotEmpty
    private String username;
    @NotBlank

    private String nomColombie;

    private double latitude; // GPS coordinates
    private double longitude;


    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotBlank String getNomColombie() {
        return nomColombie;
    }

    public void setNomColombie(@NotBlank String nomColombie) {
        this.nomColombie = nomColombie;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
