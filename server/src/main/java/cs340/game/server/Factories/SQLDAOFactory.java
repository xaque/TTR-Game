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

        return new CommandSQLDAO();
    }

    @Override
    public GameDAO getGameDAO() {

        return new GameSQLDAO();
    }

    @Override
    public LobbyDAO getLobbyDAO() {
        return new LobbySQLDAO();
    }

    @Override
    public UserDAO getUserDAO() {

        return new UserSQLDAO();
    }
}
