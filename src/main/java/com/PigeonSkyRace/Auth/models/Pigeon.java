package com.PigeonSkyRace.Auth.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


import java.util.Set;

@Document(collection = "pigeons")
public class Pigeon {
    @Id
    private String ringNumber; // Unique ring number for each pigeon
    private String gender;
    private int age;
    private String color;

    @DocumentReference
    private Breeder breeder;  // Assuming this is how you defined the breeder field.

    @DBRef
    private Set<CompetitionPigeon> competitions;

    // Constructor for Pigeon with mandatory fields
    public Pigeon(String ringNumber, String gender, int age, String color) {
        this.ringNumber = ringNumber;
        this.gender = gender;
        this.age = age;
        this.color = color;
    }

    public Set<CompetitionPigeon> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(Set<CompetitionPigeon> competitions) {
        this.competitions = competitions;
    }

    public Breeder getBreeder() {
        return breeder;
    }

    public void setRingNumber(String ringNumber) {
        this.ringNumber = ringNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBreeder(Breeder breeder) {
        this.breeder = breeder;
    }


    public Pigeon() {
    }

    // Getters
    public String getRingNumber() {
        return ringNumber;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Pigeon{" +
                "ringNumber='" + ringNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                ", breeder=" + breeder +
                '}';
    }
}

