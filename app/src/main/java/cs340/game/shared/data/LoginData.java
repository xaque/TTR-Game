package cs340.game.shared.data;

import cs340.game.shared.CommandType;

public class LoginData extends Data{

    private CommandType commandType;
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
