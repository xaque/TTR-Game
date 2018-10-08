package cs340.game.shared;

/**
 * Constant variables to be used across client and server
 */
public class CommonData {
    public static final String HOSTNAME = "localhost";
    public static final int PORT_NUMBER = 1923;
    public static final int MAX_WAITING_CONNECTIONS = 10;

    public static final String LOGIN_URI = "/login";
    public static final String LOBBY_URI = "/lobby";
    public static final String POLLER_URI = "/poller";
}
