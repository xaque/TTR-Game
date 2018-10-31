package cs340.game.shared.data;

import java.io.Serializable;

import cs340.game.shared.CommandType;

/**
 * An interface for a container for information about a command. This is for communication from the
 * Client to the Server
 */
public abstract class Data implements Serializable {
    protected CommandType commandType;

    public CommandType getCommandType() {
        return commandType;
    }
}
