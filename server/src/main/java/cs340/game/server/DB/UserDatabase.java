package cs340.game.server.DB;

import java.util.HashSet;
import java.util.Set;

import cs340.game.server.User;

/**
 * Created by Stephen on 9/28/2018.
 */

public class UserDatabase {
    private Set userSet;
    private static UserDatabase instance;

    private UserDatabase() {
        userSet = new HashSet<User>();
    }

    public static UserDatabase getInstance() {
        if(instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public String addUser(User user) {
        userSet.add(user);
        String authToken = AuthTokenDatabase.getInstance().addUser(user.getUsername());
        return authToken;
    }

    public boolean containsUser(User user) {
        return userSet.contains(user);
    }
}
