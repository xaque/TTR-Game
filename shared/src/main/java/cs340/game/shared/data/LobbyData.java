package cs340.game.shared.data;

import cs340.game.shared.CommandType;

/**
 * A container for information about a command. This is specifically for any request that has to do
 * with the game lobby.
 * @see Data
 */
public class LobbyData extends Data{
    private String gameID;
    private String username;

    public LobbyData(CommandType commandType, String gameID, String username){
        this.commandType = commandType;
        this.gameID = gameID;
        this.username = username;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
