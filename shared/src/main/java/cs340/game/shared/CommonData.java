package cs340.game.shared;

/**
 * Constant variables to be used across client and server
 */
public class CommonData {
    //TODO change to server address for client to be able to access it
    public static final String HOSTNAME = "10.24.197.129";//"192.168.1.142";//"localhost";
    public static final int PORT_NUMBER = 1923;
    public static final int MAX_WAITING_CONNECTIONS = 10;

    public static final String LOGIN_URI = "/login";
    public static final String LOBBY_URI = "/lobby";
    public static final String POLLER_URI = "/poller";
    public static final String GAME_URI = "/game";
}
