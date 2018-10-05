package cs340.game.server.Commands.LoginCommands;

import java.util.UUID;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.AuthTokenDatabase;
import cs340.game.server.DB.UserDatabase;
import cs340.game.server.User;
import cs340.game.shared.CommandData;
import cs340.game.shared.Results;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LoginData;

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
            //TODO establish expectations for return values
            return new Results(true, authToken, null);
        }
        else {
            //TODO establish exception/error format
            return new Results(false, null, null);
        }
    }
}
