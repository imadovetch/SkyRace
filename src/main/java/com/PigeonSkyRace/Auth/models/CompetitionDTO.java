package com.PigeonSkyRace.Auth.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class CompetitionDTO {

    @NotBlank
    @Size(max = 100)
    private String name; // Competition name

    @NotNull
    @JsonFormat(pattern = "yy/MM/dd HH:mm:ss")
    private LocalDateTime departureTime;



    private int pigeonCount; // Number of pigeons (must be positive)

    @Override
    public String toString() {
        return "CompetitionDTO{" +
                "name='" + name + '\'' +
                ", departureTime=" + departureTime +
                ", pigeonCount=" + pigeonCount +
                ", percentage=" + percentage +
                ", pigeonTotal=" + pigeonTotal +
                '}';
    }

    private int pigeonTotal;

    private int percentage;

    private double latitude;

    private double longitude;


    private Double distance;


    // Constructors, Getters, and Setters
    public CompetitionDTO() {}

    public CompetitionDTO(String name, LocalDateTime departureTime, int pigeonCount, int percentage) {
        this.name = name;
        this.departureTime = departureTime;
        this.pigeonCount = pigeonCount;
        this.percentage = percentage;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getPigeonTotal() {
        return pigeonTotal;
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

    public void setPigeonTotal(int pigeonTotal) {
        this.pigeonTotal = pigeonTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
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


}
