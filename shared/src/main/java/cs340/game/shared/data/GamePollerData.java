package cs340.game.shared.data;

import cs340.game.shared.CommandType;

/**
 * Created by Stephen on 10/28/2018.
 */

public class GamePollerData extends Data{
    private int sequenceNumber;
    private String username;

    public GamePollerData(int sequenceNumber, String username){
        //commandType should always be GAME_POLL
        this.commandType = CommandType.GAME_POLL;
        this.sequenceNumber = sequenceNumber;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String authtoken) {
        this.username = authtoken;
    }
}
