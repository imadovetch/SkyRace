package com.PigeonSkyRace.Auth.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class CompetitionDTO {

    @NotBlank
    @Size(max = 100)
    private String name; // Competition name

    @NotNull
    private LocalDate departureTime; // Date of the competition start

    @Positive
    private double distance; // Distance of the race (must be positive)

    @Positive
    private int pigeonCount; // Number of pigeons (must be positive)

    @Override
    public String toString() {
        return "CompetitionDTO{" +
                "name='" + name + '\'' +
                ", departureTime=" + departureTime +
                ", distance=" + distance +
                ", pigeonCount=" + pigeonCount +
                ", percentage=" + percentage +
                ", pigeons=" + pigeons.toString() +
                '}';
    }

    private int percentage; // Race completion percentage (can be 0 or higher)

    @NotNull
    private List<Pigeon> pigeons; // List of pigeons participating in the competition

    // Constructors, Getters, and Setters
    public CompetitionDTO() {}

    public CompetitionDTO(String name, LocalDate departureTime, double distance, int pigeonCount, int percentage, List<Pigeon> pigeons) {
        this.name = name;
        this.departureTime = departureTime;
        this.distance = distance;
        this.pigeonCount = pigeonCount;
        this.percentage = percentage;
        this.pigeons = pigeons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Pigeon> getPigeons() {
        return pigeons;
    }

    public void setPigeons(List<Pigeon> pigeons) {
        this.pigeons = pigeons;
    }
}
