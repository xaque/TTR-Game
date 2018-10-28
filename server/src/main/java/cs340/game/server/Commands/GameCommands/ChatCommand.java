package cs340.game.server.Commands.GameCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.GameCommandLog;
import cs340.game.server.Models.ServerGameState;
import cs340.game.shared.results.Results;
import cs340.game.shared.data.ChatData;
import cs340.game.shared.data.Data;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.results.GameResults;

//TODO stub for phase 2 implementation
public class ChatCommand implements iCommand {

    //TODO implement override stub method
    @Override
    public Results execute(Data data) {
        ChatData chatData = (ChatData)data;
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByUsername(chatData.getUsername());

        String chatMessage = chatData.getUsername() + ": "  + chatData.getChatContent();
        GameHistoryAction action = new GameHistoryAction(chatMessage, null);
        game.addGameCommand(action);
        //TODO any errors possible here?
        return new GameResults(true, null);
    }
}
