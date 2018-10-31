package cs340.game.shared.data;

import cs340.game.shared.CommandType;

/**
 * A container for information about a command. This is specifically for any request that has to do
 * with logging a player in or registering a new user.
 * @see Data
 */
public class LoginData extends Data{
    private String username;
    private String password;

    public LoginData(CommandType commandType, String username, String password){
        this.commandType = commandType;
        this.username = username;
        this.password = password;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
