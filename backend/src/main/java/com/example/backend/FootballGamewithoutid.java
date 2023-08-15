package com.example.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootballGamewithoutid {
    private String team1;
    private String team2;
    private String date;
    private String time;
}
