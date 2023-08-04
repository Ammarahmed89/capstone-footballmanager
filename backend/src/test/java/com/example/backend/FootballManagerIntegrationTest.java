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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FootballManagerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @DirtiesContext
    void addFootballGameAndExpectStatus200AndAddedFootballGame() throws Exception {
        FootballGame footballGame = new FootballGame("20:00", "Bayern", "01.08.2023", "Dortmund","18:00");

        String jsonPayload = objectMapper.writeValueAsString(footballGame);
        String mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPayload))
                        .andExpect(status().isCreated())
                        .andReturn().getResponse().getContentAsString();



        mockMvc.perform(MockMvcRequestBuilders.get("/api/games")
        ).andExpect(status().isOk());
    }
}