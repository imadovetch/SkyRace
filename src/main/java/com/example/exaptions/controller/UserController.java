package com.example.exaptions.controller;


import com.example.exaptions.Repositories.YourEntityRepository;
import com.example.exaptions.model.YourEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    YourEntityRepository yourEntityRepository;

    @GetMapping("test")
    public String test() {
        return "u)";
    }
    @GetMapping("/insertDummyData")
    public String insertDummyData() {
        // Create some dummy data
        List<YourEntity> dummyEntities = Arrays.asList(
                new YourEntity("Dummy Name 1"),
                new YourEntity("Dummy Name 2"),
                new YourEntity("Dummy Name 3")
        );

        // Save the dummy entities to the database
        yourEntityRepository.saveAll(dummyEntities);

        return "Dummy data inserted!";
    }
}
