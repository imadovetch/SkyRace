package com.PigeonSkyRace.Auth.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class Breeder {
  @Id
  private String id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 20)
  private String nomColombie;



  private String password;
  private double latitude; // GPS coordinates
  private double longitude;

  @DBRef
  private List<Pigeon> pigeons;

  private String role;



  public Breeder() {
  }

  public Breeder(String username, String password) {
    this.username = username;

    this.password = password;
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

  public List<Pigeon> getPigeons() {
    return pigeons;
  }

  public void setPigeons(List<Pigeon> pigeons) {
    this.pigeons = pigeons;
  }

  public @NotBlank @Size(max = 20) String getNomColombie() {
    return nomColombie;
  }

  public void setNomColombie(@NotBlank @Size(max = 20) String nomColombie) {
    this.nomColombie = nomColombie;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }



  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
