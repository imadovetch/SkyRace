package com.PigeonSkyRace.Auth.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document(collection = "pigeons")
public class Pigeon {
    @Id
    private String ringNumber; // Unique ring number for each pigeon
    private String gender;
    private int age;
    private String color;


    private Breeder breeder;  // Assuming this is how you defined the breeder field.


    // Constructor for Pigeon with mandatory fields
    public Pigeon(String ringNumber, String gender, int age, String color) {
        this.ringNumber = ringNumber;
        this.gender = gender;
        this.age = age;
        this.color = color;
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


}

