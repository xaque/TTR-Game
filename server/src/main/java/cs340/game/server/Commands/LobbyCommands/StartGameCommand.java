package cs340.game.server.Commands.LobbyCommands;

import java.util.ArrayList;
import java.util.Random;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DAOs.GameDAO;
import cs340.game.server.DAOs.LobbyDAO;
import cs340.game.server.DAOs.UserDAO;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.LobbyGameDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.server.Factories.DAOFactory;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LobbyData;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.User;
import cs340.game.shared.results.LobbyResults;
import cs340.game.shared.results.Results;

/**
 * Created by Stephen on 10/27/2018.
 */

public class StartGameCommand implements iCommand {
    public Results execute(Data data, DAOFactory daoFactory) {
        LobbyData lobbyData = (LobbyData)data;

        LobbyDAO lobbyDAO = daoFactory.getLobbyDAO();
        Game startingGame = lobbyDAO.getGame(lobbyData.getGameID());
        //Game startingGame = LobbyGameDatabase.getInstance().getGame(lobbyData.getGameID());

        // check if user is logged in with an authtoken
        UserDAO userDAO = daoFactory.getUserDAO();
        User u = userDAO.getUserByUsername(lobbyData.getUsername());
        String authToken = u.getAuthToken();
        if(authToken.isEmpty()){
            return new LobbyResults(false, "You are not logged in!");
        }
        /*
        try {
            String authToken = AuthTokenDatabase.getInstance().getAuthToken(lobbyData.getUsername());
        }
        catch(ServerException ex) {
            return new LobbyResults(false, ex.getMessage());
        }*/

        // check if the game to start has at least 2 players
        if(startingGame.GetGameSize() < 2) {
            ServerException ex = new ServerException("There are not enough players to start this game.");
            return new LobbyResults(false, ex.getMessage());
        }

        //randomize starting player order
        Random rand = new Random();
        ArrayList<String> usernames = startingGame.getPlayers();
        ArrayList<User> users = new ArrayList<>();
        int index;
        while(usernames.size() > 0) {
            index = rand.nextInt(usernames.size());
            String usernameToRemove = usernames.remove(index);
            User user = userDAO.getUserByUsername(usernameToRemove);
            users.add(user);
        }

        ServerGameState gameState = new ServerGameState(lobbyData.getGameID(), users);
        ActiveGamesDatabase.getInstance().addGame(gameState);

        GameDAO gameDAO = daoFactory.getGameDAO();
        gameDAO.addGame(lobbyData.getGameID(), gameState);

        LobbyGameDatabase.getInstance().startGame(startingGame);
        lobbyDAO.startGame(startingGame);
        return new LobbyResults(true, null);
    }
}
