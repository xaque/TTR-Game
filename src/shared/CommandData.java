package shared;

import java.io.Serializable;

public class CommandData implements Serializable {
    private CommandType type;
    private String data;

    /**
     * Object containing command data to be sent across a network
     * @param type The type of command
     * @param data The string to operate on
     */
    public CommandData(CommandType type, String data){
        this.type = type;
        this.data = data;
    }

    /**
     * Getter for type
     * @return The type of command
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Getter for data
     * @return The string to operate on
     */
    public String getData() {
        return data;
    }
}
