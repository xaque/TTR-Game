package cs340.game.server.Commands.LoginCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.AuthTokenDatabase;
import cs340.game.server.DB.UserDatabase;
import cs340.game.shared.LoginResults;
import cs340.game.shared.Results;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LoginData;
import cs340.game.shared.models.User;

/**
 * Created by Stephen on 9/28/2018.
 */

public class LoginCommand implements iCommand {
    public Results execute(Data data) {
        LoginData loginData = (LoginData)data;
        User user = new User(loginData.getUsername(), loginData.getPassword());
        UserDatabase userDB = UserDatabase.getInstance();
        if(userDB.containsUser(user)) {
            String authToken = AuthTokenDatabase.getInstance().addUser(user.getUsername());
            return new LoginResults(true, authToken, null);
        }
        else {
            ServerException ex = new ServerException("Invalid username/password combination.");
            return new LoginResults(false, null, ex.getMessage());
        }
    }
}
