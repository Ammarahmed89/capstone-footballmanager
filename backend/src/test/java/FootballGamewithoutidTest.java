import com.example.backend.FootballGamewithoutid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FootballGamewithoutidTest {

    @Test
    void createFootballGamewithoutidAndGetValues() {
        String team1 = "Team A";
        String team2 = "Team B";
        String date = "2023-08-15";
        String time = "15:00";

        FootballGamewithoutid footballGame = new FootballGamewithoutid(team1, team2, date, time);

        assertEquals(team1, footballGame.getTeam1());
        assertEquals(team2, footballGame.getTeam2());
        assertEquals(date, footballGame.getDate());
        assertEquals(time, footballGame.getTime());
    }

    @Test
    void testToString() {
        String team1 = "Team A";
        String team2 = "Team B";
        String date = "2023-08-15";
        String time = "15:00";

        FootballGamewithoutid footballGame = new FootballGamewithoutid(team1, team2, date, time);

        String expectedToString = "FootballGamewithoutid(team1=Team A, team2=Team B, date=2023-08-15, time=15:00)";
        assertEquals(expectedToString, footballGame.toString());
    }
}
