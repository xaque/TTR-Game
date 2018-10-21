package cs340.game.shared.models;

import java.util.ArrayList;
import java.util.List;

public class ChatLog {

    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message){
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
