package com.example.exaptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            // Attempt to get the database name to test the connection
            String dbName = mongoTemplate.getDb().getName();
            System.out.println("MongoDB connection successful! Database: " + dbName);
        } catch (Exception e) {
            System.out.println("MongoDB connection failed! Error: " + e.getMessage());
        }
    }
}

