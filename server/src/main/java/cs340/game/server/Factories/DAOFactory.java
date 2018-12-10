package cs340.game.server.Factories;

import cs340.game.server.DAOs.CommandDAO;
import cs340.game.server.DAOs.GameDAO;
import cs340.game.server.DAOs.LobbyDAO;
import cs340.game.server.DAOs.UserDAO;

public interface DAOFactory {

    public CommandDAO getCommandDAO();
    public GameDAO getGameDAO();
    public LobbyDAO getLobbyDAO();
    public UserDAO getUserDAO();
}
