package com.example.exaptions.service;

import com.example.exaptions.Repositories.YourEntityRepository;
import com.example.exaptions.model.YourEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YourEntityService {

    @Autowired
    private YourEntityRepository repository;

    public List<YourEntity> getAllEntities() {
        return repository.findAll();
    }

    public YourEntity saveEntity(YourEntity entity) {
        return repository.save(entity);
    }
}
