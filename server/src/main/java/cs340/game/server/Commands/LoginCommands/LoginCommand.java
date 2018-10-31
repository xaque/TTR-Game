package cs340.game.server.Commands.LoginCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.AuthTokenDatabase;
import cs340.game.server.DB.UserDatabase;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LoginData;
import cs340.game.shared.models.User;
import cs340.game.shared.results.LoginResults;
import cs340.game.shared.results.Results;

/**
 * Created by Stephen on 9/28/2018.
 */

public class LoginCommand implements iCommand {

    /**
     * Logs in player with correct username and password. Returns an error with an invalid username-
     * password combination.
     * @param data cast to LoginData type, contains player's username and password
     * @return Results object containing authToken if login is successful or error message if user does not exist
     */
    public Results execute(Data data) {
        LoginData loginData = (LoginData)data;
        User user = new User(loginData.getUsername(), loginData.getPassword());
        UserDatabase userDB = UserDatabase.getInstance();
        // if a valid username-password combination is entered
        if(userDB.containsUser(user)) {
            String authToken = AuthTokenDatabase.getInstance().addUser(user.getUsername());
            return new LoginResults(true, authToken, null);
        }
        // if a valid username is entered but with the wrong password
        else if(userDB.containsUsername(user.getUsername())) {
            ServerException ex = new ServerException("Incorrect password.");
            return new LoginResults(false, null, ex.getMessage());
        }
        // if an invalid username is entered
        else {
            ServerException ex = new ServerException("User does not exist.");
            return new LoginResults(false, null, ex.getMessage());
        }
    }
}
