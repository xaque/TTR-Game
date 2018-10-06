package cs340.game.client;


import cs340.game.shared.CommandData;
import cs340.game.shared.CommonData;
import cs340.game.shared.CommandType;
import cs340.game.shared.Results;
import cs340.game.shared.data.LobbyData;
import cs340.game.shared.data.LoginData;
import cs340.game.shared.models.User;

public class ServerProxy {

    /**
     * Communicates with the server to log a person in.
     * @param user contains information about the person that will be logged in
     * @return an Object containing results about the success of the request
     */
    public Results Login(User user){

        LoginData loginData = new LoginData(CommandType.LOGIN, user.getUsername(), user.getPassword());

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.LOGIN_URI, loginData);

        return results;
    }

    /**
     * Communicates with the server to register a person and then log them in.
     * @param user contains information about the person that will be registered and logged in
     * @return an Object containing results about the success of the request
     */
    public Results Register(User user){

        LoginData loginData = new LoginData(CommandType.REGISTER, user.getUsername(), user.getPassword());

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.LOGIN_URI, loginData);

        return results;
    }

    /**
     * Communicates with the server to create a new game and add the user that created it to that
     * game.
     * @param gameID the name of the game that will be created
     * @param authToken the authentication token of the user that created the game
     * @return an Object containing results about the success of the request
     */
    public Results CreateGame(String gameID, String authToken){

        LobbyData lobbyData = new LobbyData(CommandType.CREATE_GAME, gameID, authToken);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.LOBBY_URI, lobbyData);

        return results;
    }

    /**
     * Communicates with the server to add a user to a game.
     * @param gameID the name of the game to which the user with the given username will be added
     * @param authToken the authentication token of the user that is joining the game
     * @return an Object containing results about the success of the request
     */
    public Results JoinGame(String gameID, String authToken){

        LobbyData lobbyData = new LobbyData(CommandType.JOIN_GAME, gameID, authToken);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.LOBBY_URI, lobbyData);

        return results;
    }

    /**
     * Communicates with the server to start a game.
     * @param gameID the name of the game that is being started
     * @param authToken the authentication token of the user that is starting the game
     * @return an Object containing the results about the success of the request
     */
    public Results StartGame(String gameID, String authToken){

        LobbyData lobbyData = new LobbyData(CommandType.START_GAME, gameID, authToken);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.LOBBY_URI, lobbyData);

        return results;
    }
}
