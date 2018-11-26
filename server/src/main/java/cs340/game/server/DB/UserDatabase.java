package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.models.User;


/**
 * Created by Stephen on 9/28/2018.
 */

public class UserDatabase {
    private ArrayList<User> userList;
    private static UserDatabase instance;

    private UserDatabase() {
        userList = new ArrayList<>();
    }

    public static UserDatabase getInstance() {
        if(instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    /**
     * Adds the given user to the database and returns the user's new authToken
     * @param user the user to be added to the database
     * @return the created authToken to be returned to the user
     */
    public String addUser(User user) {
        userList.add(user);
        String authToken = AuthTokenDatabase.getInstance().addUser(user.getUsername());
        user.setAuthToken(authToken);
        return authToken;
    }

    /**
     * Checks if the user is contained within the database
     * @param user the user checked against the database
     * @return boolean whether the user is in the database
     */
    public boolean containsUser(User user) {
        return userList.contains(user);
    }

    /**
     * Checks if the username is contained somewhere within the database
     * @param username the username to check against the database
     * @return boolean whether the username is in the database
     */
    public boolean containsUsername(String username) {
        for(int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String username) {
        for(int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUsername().equals(username)) {
                return userList.get(i);
            }
        }
        return null;
    }
}
