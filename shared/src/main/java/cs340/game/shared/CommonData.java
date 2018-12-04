package cs340.game.shared;

/**
 * Constant variables to be used across client and server
 */
public class CommonData {
    //TODO change to server address for client to be able to access it
    //public static final String HOSTNAME = "192.168.1.142";//Zack
    //public static final String HOSTNAME = "10.37.108.191";
    public static String HOSTNAME = "10.24.207.112";//DJ
    //public static String HOSTNAME = "192.168.56.1";//Tyler
    //public static String HOSTNAME = "10.25.107.60";//Tyler
    //public static String HOSTNAME = "192.168.1.206";//"10.24.195.85";//"192.168.1.206";//Tanner
    public static final int PORT_NUMBER = 1923;
    public static final int MAX_WAITING_CONNECTIONS = 10;

    public static final String LOGIN_URI = "/login";
    public static final String LOBBY_URI = "/lobby";
    public static final String POLLER_URI = "/poller";
    public static final String GAME_URI = "/game";

    public static String PERSISTENCE_TYPE = "";
    public static int COMMANDS_BETWEEN_CHECKPOINTS = 0;

    public void setHostname(String ipaddress) {
        this.HOSTNAME = ipaddress;
    }

    public void setPersistenceType(String persistenceType){
        this.PERSISTENCE_TYPE = persistenceType;
    }

    public void setCommandsBetweenCheckpoints(int commandsBetweenCheckpoints){
        this.COMMANDS_BETWEEN_CHECKPOINTS = commandsBetweenCheckpoints;
    }
}
