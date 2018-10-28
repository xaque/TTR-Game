package cs340.game.shared.data;

import cs340.game.shared.CommandType;

/**
 * Created by Stephen on 10/28/2018.
 */

public class GamePollerData extends Data{
    private CommandType commandType;
    private int sequenceNumber;
    private String authtoken;

    public GamePollerData(CommandType commandType, int sequenceNumber, String authtoken){
        this.commandType = commandType;
        this.sequenceNumber = sequenceNumber;
        this.authtoken = authtoken;
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

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
