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
    private Map<String, String> authTokenMap;

    private static AuthTokenDatabase instance;

    private AuthTokenDatabase() {
        authTokenMap = new HashMap<>();
    }

    public static AuthTokenDatabase getInstance() {
        if (instance == null) {
            instance = new AuthTokenDatabase();
        }
        return instance;
    }

    /**
     * Returns an authToken of a given username contained in the database
     * @param username The name of the player seeking their authToken
     * @return the authToken of the given user
     * @throws ServerException contains error message stating that the user with the given username
     *          is not logged in
     */
    public String getAuthToken(String username) throws ServerException {
        String authToken = (String)authTokenMap.get(username);
        if(authToken != null) {
            return authToken;
        }
        else {
            throw new ServerException("User not logged in.");
        }
    }

    /**
     * Adds the given user's username to the database, attaches an authToken to the user within the
     * database, and returns the authToken created.
     * @param username the name of the player being added to the database
     * @return the newly created authToken
     */
    public String addUser(String username) {
        String authToken = UUID.randomUUID().toString();
        authTokenMap.put(username, authToken);
        return authToken;
    }

    public String getUsernameByAuthToken(String authToken) {
        for(Map.Entry<String, String> entry : authTokenMap.entrySet()) {
            if(entry.getValue().equals(authToken)) {
                return entry.getKey();
            }
        }
        return null;
    }
}