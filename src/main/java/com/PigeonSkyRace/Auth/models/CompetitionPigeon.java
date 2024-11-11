package com.PigeonSkyRace.Auth.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Document(collection = "competition_pigeons")
public class CompetitionPigeon {
    @Id
    private String id;

    @DBRef
    private Competition competition;

    @DBRef
    private Pigeon pigeon;


    @JsonFormat(pattern = "yy/MM/dd HH:mm:ss")
    private LocalTime EndTime;

    private Double distance ;
    private Double vitesse ;
    private Double score;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Pigeon getPigeon() {
        return pigeon;
    }

    public void setPigeon(Pigeon pigeon) {
        this.pigeon = pigeon;
    }

    public LocalTime getEndTime() {
        return EndTime;
    }

    public void setEndTime(LocalTime endTime) {
        EndTime = endTime;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getVitesse() {
        return vitesse;
    }

    public void setVitesse(Double vitesse) {
        this.vitesse = vitesse;
    }


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "CompetitionPigeon{" +
                "id='" + id + '\'' +
                ", competition=" + competition +
                ", pigeon=" + pigeon +
                ", EndTime=" + EndTime +
                ", distance=" + distance +
                ", vitesse=" + vitesse +
                ", score=" + score +
                '}';
    }
}
