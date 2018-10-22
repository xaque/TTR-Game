package cs340.game.shared.models;

public class Message {

    private String player;
    private String message;
    private MessageType type;

    public Message(String player, String message, MessageType type){
        this.player = player;
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public enum MessageType{
        CHAT, ACTION
    }
}
