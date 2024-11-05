package com.PigeonSkyRace.Auth.models;

import org.springframework.stereotype.Component;

@Component
public class PigeonResponseDto {
    private String ringNumber;
    private String gender;
    private int age;
    private String color;
    private BreederDto breeder;


    public String getRingNumber() {
        return ringNumber;
    }

    public void setRingNumber(String ringNumber) {
        this.ringNumber = ringNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BreederDto getBreeder() {
        return breeder;
    }

    public void setBreeder(BreederDto breeder) {
        this.breeder = breeder;
    }

    @Override
    public String toString() {
        return "PigeonResponseDto{" +
                "ringNumber='" + ringNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                ", breeder=" + breeder +
                '}';
    }
}

