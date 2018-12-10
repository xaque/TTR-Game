package cs340.game.server.Factories;

import cs340.game.server.DAOs.CommandDAO;
import cs340.game.server.DAOs.CommandInMemoryDAO;
import cs340.game.server.DAOs.GameDAO;
import cs340.game.server.DAOs.GameInMemoryDAO;
import cs340.game.server.DAOs.LobbyDAO;
import cs340.game.server.DAOs.LobbyInMemoryDAO;
import cs340.game.server.DAOs.UserDAO;
import cs340.game.server.DAOs.UserInMemoryDAO;

public class InMemoryDAOFactory implements DAOFactory{

    @Override
    public CommandDAO getCommandDAO() {

        return new CommandInMemoryDAO();
    }

    @Override
    public GameDAO getGameDAO() {

        return new GameInMemoryDAO();
    }

    @Override
    public LobbyDAO getLobbyDAO() {
        return new LobbyInMemoryDAO();
    }

    @Override
    public UserDAO getUserDAO() {

        return new UserInMemoryDAO();
    }
}
