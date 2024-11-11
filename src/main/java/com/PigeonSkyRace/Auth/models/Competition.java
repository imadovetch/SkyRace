package com.PigeonSkyRace.Auth.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @JsonFormat(pattern = "yy/MM/dd HH:mm:ss")
    private LocalDateTime departureTime;

    private int pigeonTotal;

    private int percentage;

    private int pigeonCount;


    private boolean status;

    private boolean started;

    @DBRef
    private Set<CompetitionPigeon> pigeons;


    public Competition() {}

    public Competition(String name, double latitude, double longitude, LocalDateTime departureTime,  int pigeonCount, int percentage, boolean status) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.departureTime = departureTime;
        this.pigeonCount = pigeonCount;
        this.percentage = percentage;
        this.status = status;

    }


    // Getters and Setters


    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getPigeonTotal() {
        return pigeonTotal;
    }

    public void setPigeonTotal(int pigeonTotal) {
        this.pigeonTotal = pigeonTotal;
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<CompetitionPigeon> getPigeons() {
        return pigeons;
    }

    public void setPigeons(Set<CompetitionPigeon> pigeons) {
        this.pigeons = pigeons;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", departureTime=" + departureTime +
                ", pigeonCount=" + pigeonCount +
                ", percentage=" + percentage +
                ", status=" + status +
                ", pigeons=" + pigeons +
                '}';
    }
}
