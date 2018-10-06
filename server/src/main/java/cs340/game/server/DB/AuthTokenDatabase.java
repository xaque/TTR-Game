package cs340.game.server.DB;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cs340.game.shared.ServerException;

/**
 * Created by Stephen on 9/28/2018.
 */

public class AuthTokenDatabase {

    //format: <key=username, val=authToken>
    private Map authTokenMap;

    private static AuthTokenDatabase instance;

    private AuthTokenDatabase() {
        authTokenMap = new HashMap<String, String>();
    }

    public static AuthTokenDatabase getInstance() {
        if (instance == null) {
            instance = new AuthTokenDatabase();
        }
        return instance;
    }

    public String getAuthToken(String username) throws ServerException {
        String authToken = (String)authTokenMap.get(username);
        if(authToken != null) {
            return authToken;
        }
        else {
            throw new ServerException("User not logged in.");
        }
    }

    public String addUser(String username) {
        String authToken = UUID.randomUUID().toString();
        authTokenMap.put(username, authToken);
        return authToken;
    }
}