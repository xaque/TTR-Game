package cs340.game.shared.data;

import cs340.game.shared.CommandType;

/**
 * Created by Stephen on 10/22/2018.
 */

public class ChatData extends Data {
    private String username;
    private String chatContent;

    public ChatData(String username, String chatContent) {
        //commandType should always be chat
        this.commandType = CommandType.CHAT;
        this.username = username;
        this.chatContent = chatContent;
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

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}
