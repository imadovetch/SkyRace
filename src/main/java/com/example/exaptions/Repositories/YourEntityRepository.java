package com.example.exaptions.Repositories;


import com.example.exaptions.model.YourEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YourEntityRepository extends MongoRepository<YourEntity, String> {
}
