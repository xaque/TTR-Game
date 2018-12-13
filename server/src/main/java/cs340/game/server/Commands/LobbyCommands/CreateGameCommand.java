package cs340.game.server.Commands.LobbyCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DAOs.LobbyDAO;
import cs340.game.server.DAOs.UserDAO;
import cs340.game.server.DB.LobbyGameDatabase;
import cs340.game.server.Factories.DAOFactory;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LobbyData;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.User;
import cs340.game.shared.results.LobbyResults;
import cs340.game.shared.results.Results;

/**
 * Created by Stephen on 9/28/2018.
 */

public class CreateGameCommand implements iCommand {

    /**
     * Creates a game with name and user sent in data. Checks to make sure user is logged in and
     * that game name does not already exist.
     * @param data cast to type LobbyData, contains game name and username of the game creator
     * @return Results object stating success of creating the game and a potential error message
     */
    public Results execute(Data data, DAOFactory daoFactory) {
        LobbyData lobbyData = (LobbyData)data;

        // check if user is logged in with an authToken
        UserDAO userDAO = daoFactory.getUserDAO();
        User user = userDAO.getUserByUsername(lobbyData.getUsername());

        String authToken = user.getAuthToken();
        if(authToken.isEmpty()){
            return new LobbyResults(false, "You are not logged in!");
        }

        LobbyDAO lobbyDAO = daoFactory.getLobbyDAO();
        // check if a game already exists with the entered name
        if(lobbyDAO.getGame(lobbyData.getGameID()) != null){
            System.out.println("1");
            ServerException ex = new ServerException("There is already a game in the lobby with this name.");
            return new LobbyResults(false, ex.getMessage());
        }

        Game game = new Game(lobbyData.getGameID(), lobbyData.getUsername());
        LobbyGameDatabase.getInstance().addGame(game);
        lobbyDAO.addGame(game);
        return new LobbyResults(true, null);

        /*
        // check if a game already exists with the entered name
        if(LobbyGameDatabase.getInstance().getGame(lobbyData.getGameID()) != null) {
            ServerException ex = new ServerException("There is already a game in the lobby with this name.");
            return new LobbyResults(false, ex.getMessage());
        }

        Game game = new Game(lobbyData.getGameID(), lobbyData.getUsername());
        LobbyGameDatabase.getInstance().addGame(game);
        return new LobbyResults(true, null);
        */
    }
}
