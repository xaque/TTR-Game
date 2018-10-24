package cs340.game.server.Commands.LoginCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.UserDatabase;
import cs340.game.shared.results.LoginResults;
import cs340.game.shared.results.Results;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LoginData;
import cs340.game.shared.models.User;

/**
 * Created by Stephen on 9/28/2018.
 */

public class RegisterCommand implements iCommand{

    /**
     * Registers a user with the given username and password. Notifies the user if the username is
     * already taken.
     * @param data cast to LoginData, contains username and password
     * @return Results object with success of registering and authToken, or error message stating the given username is taken
     */
    public Results execute(Data data) {
        LoginData loginData = (LoginData)data;
        User user = new User(loginData.getUsername(), loginData.getPassword());

        // make sure no field is empty
        if(user.getUsername() == null || user.getPassword() == null) {
            ServerException ex = new ServerException("Please enter a value for both fields.");
            return new LoginResults(false, null, ex.getMessage());
        }

        UserDatabase userDB = UserDatabase.getInstance();

        // check if a user already exists with this username
        if(userDB.containsUsername(user.getUsername())) {
            ServerException ex = new ServerException("A user already exists with this username.");
            return new LoginResults(false, null, ex.getMessage());
        }
        String authToken = UserDatabase.getInstance().addUser(user);
        return new LoginResults(true, authToken, null);
    }
}
