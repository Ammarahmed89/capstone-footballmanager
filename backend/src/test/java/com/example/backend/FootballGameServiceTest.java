package com.example.backend;

        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;

        import static org.assertj.core.api.Assertions.assertThat;
        import static org.mockito.Mockito.*;

public class FootballGameServiceTest {

    @Mock
    private FootballGameRepository footballGameRepository;

    @InjectMocks
    private FootballGameService footballGameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenMockGames_whenGetAllGames_thenReturnGamesList() {
        // Arrange
        List<FootballGame> mockGames = new ArrayList<>();
        mockGames.add(new FootballGame("1", "Team A", "Team B", "2023-07-30", "15:00"));
        mockGames.add(new FootballGame("2", "Team C", "Team D", "2023-07-31", "16:30"));

        when(footballGameRepository.findAll()).thenReturn(mockGames);

        // Act
        List<FootballGame> games = footballGameService.getAllGames();

        // Assert
        assertThat(games).hasSize(2);
        assertThat(games).containsExactlyElementsOf(mockGames);
    }

    @Test
    void givenMockGame_whenGetGameById_thenReturnGame() {
        // Arrange
        FootballGame mockGame = new FootballGame("1", "Team X", "Team Y", "2023-08-01", "18:00");

        when(footballGameRepository.findById("1")).thenReturn(Optional.of(mockGame));

        // Act
        Optional<FootballGame> game = footballGameService.getGameById("1");

        // Assert
        assertThat(game).isPresent().contains(mockGame);
    }

    @Test
    void givenGameToAdd_whenAddGame_thenReturnSavedGame() {
        // Arrange
        FootballGame gameToAdd = new FootballGame(null, "Team X", "Team Y", "2023-08-01", "18:00");
        FootballGame savedGame = new FootballGame("1", "Team X", "Team Y", "2023-08-01", "18:00");

        when(footballGameRepository.save(gameToAdd)).thenReturn(savedGame);

        // Act
        FootballGame addedGame = footballGameService.addGame(gameToAdd);

        // Assert
        assertThat(addedGame).isEqualTo(savedGame);
    }

    @Test
    void givenExistingGameAndUpdatedGame_whenUpdateGame_thenGameIsUpdated() {
        // Arrange
        FootballGame existingGame = new FootballGame("1", "Team A", "Team B", "2023-07-30", "15:00");
        FootballGame updatedGame = new FootballGame("1", "Team X", "Team Y", "2023-08-01", "18:00");

        when(footballGameRepository.findById("1")).thenReturn(Optional.of(existingGame));
        when(footballGameRepository.save(existingGame)).thenReturn(updatedGame);

        // Act
        footballGameService.updateGame("1", updatedGame);

        // Assert
        assertThat(existingGame).isEqualTo(updatedGame);
    }

    @Test
    void givenGameId_whenDeleteGame_thenRepositoryDeletesGame() {
        // Arrange
        String gameId = "1";

        // Act
        footballGameService.deleteGame(gameId);

        // Assert
        verify(footballGameRepository, times(1)).deleteById(gameId);
    }
}
