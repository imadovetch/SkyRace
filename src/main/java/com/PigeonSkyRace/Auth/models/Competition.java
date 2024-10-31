package com.PigeonSkyRace.Auth.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "competitions") // Maps to a MongoDB collection
public class Competition {

    @Id
    private String id; // MongoDB automatically uses the _id field

    @NotBlank
    @Size(max = 100)
    private String name; // Name of the competition

    private double latitude;

    private double longitude;

    @NotEmpty
    private LocalDate departureTime; // Date of the competition start

    private double distance; // Total distance of the race

    private int pigeonCount; // Number of pigeons participating

    private int percentage; // Completion percentage of the race

    private boolean status; // Status of the competition (ongoing, finished, etc.)

    @DocumentReference
    private List<Pigeon> pigeons; // Reference to the pigeons associated with the competition

    // Constructors, Getters, and Setters
    public Competition() {}

    public Competition(String name, double latitude, double longitude, LocalDate departureTime, double distance, int pigeonCount, int percentage, boolean status, List<Pigeon> pigeons) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.departureTime = departureTime;
        this.distance = distance;
        this.pigeonCount = pigeonCount;
        this.percentage = percentage;
        this.status = status;
        this.pigeons = pigeons;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPigeonCount() {
        return pigeonCount;
    }

    public void setPigeonCount(int pigeonCount) {
        this.pigeonCount = pigeonCount;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Pigeon> getPigeons() {
        return pigeons;
    }

    public void setPigeons(List<Pigeon> pigeons) {
        this.pigeons = pigeons;
    }
}
