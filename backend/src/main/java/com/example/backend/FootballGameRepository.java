package com.example.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FootballGameRepository extends MongoRepository<FootballGame, String> {
}
