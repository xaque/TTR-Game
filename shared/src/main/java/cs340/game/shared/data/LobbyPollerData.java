package cs340.game.shared.data;

import cs340.game.shared.CommandType;

/**
 * A container for information about a command. This is specifically for any request that has to do
 * with the Poller.
 * @see Data
 * @see cs340.game.client
 */
public class LobbyPollerData extends Data{
    private int sequenceNumber;

    public LobbyPollerData(int sequenceNumber){
        //commandType should always be LOBBY_POLL
        this.commandType = CommandType.LOBBY_POLL;
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
