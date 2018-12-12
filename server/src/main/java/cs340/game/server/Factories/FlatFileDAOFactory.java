package cs340.game.server.Factories;

import cs340.game.server.DAOs.CommandDAO;
import cs340.game.server.DAOs.CommandFlatFileDAO;
import cs340.game.server.DAOs.GameDAO;
import cs340.game.server.DAOs.GameFlatFileDAO;
import cs340.game.server.DAOs.LobbyDAO;
import cs340.game.server.DAOs.LobbyFlatFileDAO;
import cs340.game.server.DAOs.UserDAO;
import cs340.game.server.DAOs.UserFlatFileDAO;

public class FlatFileDAOFactory implements DAOFactory{

    @Override
    public CommandDAO getCommandDAO() {

        return CommandFlatFileDAO.getInstance();
    }

    @Override
    public GameDAO getGameDAO() {

        return GameFlatFileDAO.getInstance();
    }

    @Override
    public LobbyDAO getLobbyDAO() {
        return LobbyFlatFileDAO.getInstance();
    }

    @Override
    public UserDAO getUserDAO() {

        return UserFlatFileDAO.getInstance();
    }
}
