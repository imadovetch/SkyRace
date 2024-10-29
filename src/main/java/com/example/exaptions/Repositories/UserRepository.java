package com.example.exaptions.Repositories;

import com.example.exaptions.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);
}
