package cs340.game.shared;

/**
 * Constant variables to be used across client and server
 */
public class CommonData {
    //TODO change to server address for client to be able to access it
    //public static final String HOSTNAME = "192.168.1.142";//Zack
    //public static final String HOSTNAME = "10.37.108.191";
    //public static final String HOSTNAME = "10.24.203.81";//DJ
    //public static String HOSTNAME = "192.168.56.1";//Tyler
    //public static String HOSTNAME = "10.25.107.60";//Tyler
    public static String HOSTNAME = "10.24.195.85";//"192.168.1.206";//Tanner
    public static final int PORT_NUMBER = 1923;
    public static final int MAX_WAITING_CONNECTIONS = 10;

    public static final String LOGIN_URI = "/login";
    public static final String LOBBY_URI = "/lobby";
    public static final String POLLER_URI = "/poller";
    public static final String GAME_URI = "/game";

    public void setHostname(String ipaddress) {
        this.HOSTNAME = ipaddress;
    }
}
