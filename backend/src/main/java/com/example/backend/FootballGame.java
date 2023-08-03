package com.example.backend;


import lombok.Data;
@Data
public class FootballGame {

    private String id;
    private String team1;
    private String team2;
    private String date;
    private String time;

    public FootballGame(Object o, String teamA, String teamB, String date, String s) {
    }

    public void setId(String id) {
    }


    // Konstruktor, Getter und Setter (nicht gezeigt für die Einfachheit)
}

