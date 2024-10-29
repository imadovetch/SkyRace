package com.example.exaptions.controller;

import com.example.exaptions.model.User;
import com.example.exaptions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        return userService.saveUser(user);
//    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("test")
    public String test() {
        return "u)";
    }

//    @GetMapping("/{name}")
//    public User getUserByName(@PathVariable String name) {
//        return userService.getUserByName(name);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable String id) {
//        userService.deleteUser(id);
//    }
}
