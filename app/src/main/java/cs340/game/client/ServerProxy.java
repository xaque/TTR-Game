package cs340.game.client;

import cs340.game.shared.CommandType;
import cs340.game.shared.Results;
import cs340.game.shared.data.LobbyData;
import cs340.game.shared.data.LoginData;
import cs340.game.shared.models.User;

public class ServerProxy {

    public Results Login(User user){

        LoginData loginData = new LoginData(CommandType.LOGIN, user.getUsername(), user.getPassword());

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send("/login", loginData);

        return results;
    }

    public Results Register(User user){

        LoginData loginData = new LoginData(CommandType.REGISTER, user.getUsername(), user.getPassword());

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send("/login", loginData);

        return results;
    }

    public Results CreateGame(String gameID, String username){

        LobbyData lobbyData = new LobbyData(CommandType.CREATE_GAME, gameID, username);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send("/lobby", lobbyData);

        return results;
    }

    public Results JoinGame(String gameID, String username){

        LobbyData lobbyData = new LobbyData(CommandType.JOIN_GAME, gameID, username);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send("/lobby", lobbyData);

        return results;
    }
}
