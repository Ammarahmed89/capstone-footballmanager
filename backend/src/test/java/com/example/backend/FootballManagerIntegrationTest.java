package com.example.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FootballManagerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FootballGameRepository footballGameRepository; // Inject the repository

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DirtiesContext
    void addFootballGameAndExpectStatus201AndAddedFootballGame() throws Exception {
        FootballGame footballGame = new FootballGame("1", "Bayern", "Dortmund", "01.08.2023","20:00");

        String jsonPayload = objectMapper.writeValueAsString(footballGame);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.team1").value("Bayern"))
                .andExpect(jsonPath("$.team2").value("Dortmund"))
                .andExpect(jsonPath("$.date").value("01.08.2023"))
                .andExpect(jsonPath("$.time").value("20:00"));
    }

    @Test
    @DirtiesContext
    void updateFootballGameAndExpectStatus204AndUpdatedFootballGame() throws Exception {
        FootballGame existingGame = new FootballGame("1", "Bayern", "Dortmund", "01.08.2023","18:00");
        existingGame = footballGameRepository.save(existingGame);

        FootballGame updatedGame = new FootballGame("1", "Updated Team A", "Updated Team B", "02.08.2023", "19:00");
        String jsonPayload = objectMapper.writeValueAsString(updatedGame);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/games/{id}", existingGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/games/{id}", existingGame.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingGame.getId()))
                .andExpect(jsonPath("$.team1").value("Updated Team A"))
                .andExpect(jsonPath("$.team2").value("Updated Team B"))
                .andExpect(jsonPath("$.date").value("02.08.2023"))
                .andExpect(jsonPath("$.time").value("19:00"));
    }

    @Test
    @DirtiesContext
    void deleteFootballGameAndExpectStatus204() throws Exception {
        FootballGame existingGame = new FootballGame("1", "Bayern", "Dortmund", "01.08.2023","18:00");
        existingGame = footballGameRepository.save(existingGame);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/games/{id}", existingGame.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/games/{id}", existingGame.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    void getFootballGameByIdAndExpectStatus200() throws Exception {
        FootballGame existingGame = new FootballGame("1", "Bayern", "Dortmund", "01.08.2023","18:00");
        existingGame = footballGameRepository.save(existingGame);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/games/{id}", existingGame.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingGame.getId()))
                .andExpect(jsonPath("$.team1").value("Bayern"))
                .andExpect(jsonPath("$.team2").value("Dortmund"))
                .andExpect(jsonPath("$.date").value("01.08.2023"))
                .andExpect(jsonPath("$.time").value("18:00"));
    }
}
