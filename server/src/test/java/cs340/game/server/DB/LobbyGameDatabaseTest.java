package cs340.game.server.DB;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs340.game.shared.models.Game;
import cs340.game.shared.models.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LobbyGameDatabaseTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game("testgame", "donovan");
    }

    @Test
    void getInstance() {
        assertNotNull(LobbyGameDatabase.getInstance());
    }

    @Test
    void addGame() {
        LobbyGameDatabase.getInstance().addGame(game);
    }

    @Test
    void removeGame() {
        addGame();
        LobbyGameDatabase.getInstance().removeGame("testgame");
        assertNull(LobbyGameDatabase.getInstance().getGame("testgame"));
    }

    @Test
    void getGame() {
        addGame();
        Game g = LobbyGameDatabase.getInstance().getGame("testgame");
        assertTrue(game.equals(g));
    }

    @Test
    void addPlayerToGame() {
        addGame();
        User u2 = new User("lenny", "apoisf8903");
        LobbyGameDatabase.getInstance().addPlayerToGame("lenny", game);
    }

    @AfterEach
    void tearDown(){
        LobbyGameDatabase.getInstance().clearDatabase();
    }
}