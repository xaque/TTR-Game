package cs340.game.client;


import java.util.List;

import cs340.game.shared.CommandType;
import cs340.game.shared.CommonData;
import cs340.game.shared.data.ChatData;
import cs340.game.shared.data.DestinationCardData;
import cs340.game.shared.data.LobbyData;
import cs340.game.shared.data.LoginData;
import cs340.game.shared.data.TrainCardData;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.models.User;
import cs340.game.shared.results.Results;

public class ServerProxy {

    public Results DrawTrainCard(String authToken){

        TrainCardData cardData = new TrainCardData(CommandType.DRAW_TRAIN_CARD, authToken,null);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.GAME_URI, cardData);

        return results;
    }

    public Results DiscardTrainCard(String authToken, TrainCard card){

        TrainCardData cardData = new TrainCardData(CommandType.DISCARD_TRAIN_CARD, authToken, card);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.GAME_URI, cardData);

        return results;
    }

    public Results DrawDestinationCard(String authToken){

        TrainCardData cardData = new TrainCardData(CommandType.DRAW_DESTINATION_CARD, authToken, null);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.GAME_URI, cardData);

        return results;
    }

    public Results DiscardDestinationCards(String authToken, List<DestinationCard> cards){

        DestinationCardData cardData = new DestinationCardData(CommandType.DISCARD_DESTINATION_CARD, authToken, cards);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.GAME_URI, cardData);

        return results;
    }

    public Results SendChat(String authToken, String message){

        ChatData chatData = new ChatData(CommandType.CHAT, authToken, message);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send(CommonData.GAME_URI, chatData);

        return results;
    }

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
