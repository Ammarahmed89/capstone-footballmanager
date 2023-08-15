package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class FootballGameController {

    private final FootballGameService footballGameService;



    @GetMapping
    public ResponseEntity<List<FootballGame>> getAllGames() {
        List<FootballGame> games = footballGameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FootballGame> getGameById(@PathVariable String id) {
        Optional<FootballGame> game = footballGameService.getGameById(id);
        return game.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FootballGame> addGame(@RequestBody FootballGamewithoutid game2 ) {
        FootballGame addedGame = footballGameService.addGame(game2);
        return new ResponseEntity<>(addedGame, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGame(@PathVariable String id, @RequestBody FootballGame updatedGame) {
        footballGameService.updateGame(id, updatedGame);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {
        footballGameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

}