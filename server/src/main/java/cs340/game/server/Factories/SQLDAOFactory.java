package cs340.game.server.Factories;

import cs340.game.server.DAOs.CommandDAO;
import cs340.game.server.DAOs.CommandSQLDAO;
import cs340.game.server.DAOs.GameDAO;
import cs340.game.server.DAOs.GameSQLDAO;
import cs340.game.server.DAOs.LobbyDAO;
import cs340.game.server.DAOs.LobbySQLDAO;
import cs340.game.server.DAOs.UserDAO;
import cs340.game.server.DAOs.UserSQLDAO;

public class SQLDAOFactory implements DAOFactory{

    @Override
    public CommandDAO getCommandDAO() {
        return CommandSQLDAO.getInstance();
    }

    @Override
    public GameDAO getGameDAO() {
        return GameSQLDAO.getInstance();
    }

    @Override
    public LobbyDAO getLobbyDAO() {
        return LobbySQLDAO.getInstance();
    }

    @Override
    public UserDAO getUserDAO() {
        return UserSQLDAO.getInstance();
    }
}
