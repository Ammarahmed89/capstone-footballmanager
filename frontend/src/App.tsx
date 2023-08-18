import { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

export interface FootballGame {
    id: string;
    team1: string;
    team2: string;
    date: string;
    time: string;
}

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [selectedGame, setSelectedGame] = useState<FootballGame | null>(null);
    const [gameIdInput, setGameIdInput] = useState('');
    const [newGame, setNewGame] = useState<FootballGame>({
        id: '',
        team1: '',
        team2: '',
        date: '',
        time: '',
    });
    const [addedGames, setAddedGames] = useState<FootballGame[]>([]);

    const handleGetGameById = () => {
        axios.get(`/api/games/${gameIdInput}`)
            .then((response) => {
                setSelectedGame(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const handleAddGame = () => {
        axios.post('/api/games', newGame)
            .then((response) => {
                const addedGameResponse: FootballGame = response.data;
                setAddedGames((prevAddedGames) => [...prevAddedGames, addedGameResponse]);
                setNewGame({
                    id: '',
                    team1: '',
                    team2: '',
                    date: '',
                    time: '',
                });
            })
            .catch((error) => {
                console.error('Error adding game:', error);
            });
    };

    const handleDeleteGame = (gameId: string) => {
        axios.delete(`/api/games/${gameId}`)
            .then(() => {
                console.log('Game deleted:', gameId);
                setSelectedGame(null);
                handleShowAllGames();
            })
            .catch((error) => {
                console.error('Error deleting game:', error);
            });
    };

    const handleEditGame = (gameId: string) => {
        const gameToEdit = addedGames.find((game) => game.id === gameId);
        if (gameToEdit) {
            const updatedTeam1 = prompt('Edit Team 1:', gameToEdit.team1);
            const updatedTeam2 = prompt('Edit Team 2:', gameToEdit.team2);
            const updatedDate = prompt('Edit Date:', gameToEdit.date);
            const updatedTime = prompt('Edit Time:', gameToEdit.time);

            if (updatedTeam1 && updatedTeam2 && updatedDate && updatedTime) {
                const updatedGame: FootballGame = {
                    ...gameToEdit,
                    team1: updatedTeam1,
                    team2: updatedTeam2,
                    date: updatedDate,
                    time: updatedTime,
                };

                axios.put(`/api/games/${gameId}`, updatedGame)
                    .then(() => {
                        console.log('Game updated:', updatedGame);
                        handleShowAllGames();
                    })
                    .catch((error) => {
                        console.error('Error updating game:', error);
                    });
            }
        }
    };

    const handleShowAllGames = () => {
        axios.get('/api/games')
            .then((response) => {
                setAddedGames(response.data);
            })
            .catch((error) => {
                console.error('Error getting all games:', error);
            });
    };

    useEffect(() => {
        handleShowAllGames();
    }, []);

    const handleLogin = () => {
        // Implement your login logic here
        // For example, check the credentials and set isLoggedIn to true
        setIsLoggedIn(true);
    };

    const handleLogout = () => {
        setIsLoggedIn(false);
        setUsername('');
        setPassword('');
    };

    return (
        <div className="App">
            <h1>Football Games</h1>
            {isLoggedIn ? (
                <>
                    <h2>Welcome, {username}!</h2>
                    <button onClick={handleLogout}>Logout</button>
                    <h2>Get Game</h2>
                    <div>
                        <input
                            type="text"
                            placeholder="Enter game ID"
                            value={gameIdInput}
                            onChange={(e) => setGameIdInput(e.target.value)}
                        />
                        <button onClick={handleGetGameById}>Get Game</button>
                    </div>
                    {selectedGame && (
                        <div className="selected-game">
                            <p>
                                <strong>Team 1:</strong> {selectedGame.team1} VS {selectedGame.team2}
                            </p>
                            <p>
                                <strong>Date:</strong> {selectedGame.date} | <strong>Time:</strong> {selectedGame.time}
                            </p>
                            <button onClick={() => handleDeleteGame(selectedGame.id)}>Delete Game</button>
                        </div>
                    )}
                    <div className="add-game">
                        <h2>Add New Game:</h2>
                        <input
                            type="text"
                            placeholder="Team 1"
                            value={newGame.team1}
                            onChange={(e) => setNewGame({ ...newGame, team1: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Team 2"
                            value={newGame.team2}
                            onChange={(e) => setNewGame({ ...newGame, team2: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Date"
                            value={newGame.date}
                            onChange={(e) => setNewGame({ ...newGame, date: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Time"
                            value={newGame.time}
                            onChange={(e) => setNewGame({ ...newGame, time: e.target.value })}
                        />
                        <button onClick={handleAddGame}>Add Game</button>
                    </div>
                    <div className="added-game">
                        <h2>All Games:</h2>
                        {addedGames.map((game) => (
                            <div key={game.id}>
                                <p>
                                    <strong>Team 1:</strong> {game.team1} VS {game.team2}
                                </p>
                                <p>
                                    <strong>Date:</strong> {game.date} | <strong>Time:</strong> {game.time}
                                </p>
                                <button onClick={() => handleEditGame(game.id)}>Edit Game</button>
                                <button onClick={() => handleDeleteGame(game.id)}>Delete Game</button>
                            </div>
                        ))}
                    </div>
                </>
            ) : (
                <div className="login">
                    <h2>Login</h2>
                    <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
                    <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
                    <button onClick={handleLogin}>Login</button>
                </div>
            )}
        </div>
    );
}

export default App;
