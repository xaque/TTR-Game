package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.models.Game;

public interface LobbyDAO {

    void addGame(Game game);
    void addPlayerToGame(String username, Game game);
    Game getGame(String gameName);
    void startGame(Game game);
}
