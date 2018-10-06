package cs340.game.server.Commands.LoginCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.UserDatabase;
import cs340.game.server.User;
import cs340.game.shared.LoginResults;
import cs340.game.shared.Results;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LoginData;

/**
 * Created by Stephen on 9/28/2018.
 */

public class RegisterCommand implements iCommand{
    public Results execute(Data data) {
        LoginData loginData = (LoginData)data;
        User user = new User(loginData.getUsername(), loginData.getPassword());
        String authToken = UserDatabase.getInstance().addUser(user);
        return new LoginResults(true, authToken, null);
    }
}
