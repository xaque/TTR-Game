package cs340.game.server.Commands.GameCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.data.ChatData;
import cs340.game.shared.data.Data;
import cs340.game.shared.results.GameResults;
import cs340.game.shared.results.Results;

public class ChatCommand implements iCommand {

    @Override
    public Results execute(Data data) {
        System.out.println("Chat Command");
        ChatData chatData = (ChatData)data;
        System.out.println(chatData.getChatContent());
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByUsername("a");

        String chatMessage = chatData.getUsername() + ": "  + chatData.getChatContent();
        GameHistoryAction action = new GameHistoryAction(chatMessage, null);
        game.addGameCommand(action);
        //TODO any errors possible here?
        return new GameResults(true, null);
    }
}
