package com.example.backend;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data

public class FootballGame {
    @Id
    private  String id;
    private String team1;
    private String team2;
    private String date;
    private String time;

    public FootballGame(String id,String team1, String team2, String date, String time) {
        this.id= id;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.time = time;
    }
}

