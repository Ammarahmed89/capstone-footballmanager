package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballGameService {

    private final FootballGameRepository footballGameRepository;



    public List<FootballGame> getAllGames() {
        return footballGameRepository.findAll();
    }

    public Optional<FootballGame> getGameById(String id) {
        return footballGameRepository.findById(id);
    }

    public FootballGame addGame(FootballGamewithoutid game2) {
        FootballGame game3= new FootballGame(null, game2.getTeam1(), game2.getTeam2(), game2.getDate(), game2.getTime());
        return footballGameRepository.save(game3);
    }

    public void updateGame(String id, FootballGame updatedGame) {
        Optional<FootballGame> existingGame = footballGameRepository.findById(id);
        if (existingGame.isPresent()) {
            FootballGame game = existingGame.get();
            game.setTeam1(updatedGame.getTeam1());
            game.setTeam2(updatedGame.getTeam2());
            game.setDate(updatedGame.getDate());
            game.setTime(updatedGame.getTime());
            footballGameRepository.save(game);
        }
    }

    public void deleteGame(String id) {
        footballGameRepository.deleteById(id);
    }
}