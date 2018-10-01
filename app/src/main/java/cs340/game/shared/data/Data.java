package cs340.game.shared.data;

import java.io.Serializable;

import cs340.game.shared.CommandType;

public abstract class Data implements Serializable {
    private CommandType commandType;

    public CommandType getCommandType() {
        return commandType;
    }
}
