package cs340.game.shared.data;

import cs340.game.shared.CommandType;

public class PollerData extends Data{

    private CommandType commandType;
    private int sequenceNumber;

    public PollerData(CommandType commandType, int sequenceNumber){
        this.commandType = commandType;
        this.sequenceNumber = sequenceNumber;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}