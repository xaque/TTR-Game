package cs340.game.server.Commands;

import cs340.game.server.DAOs.CommandDAO;
import cs340.game.server.DAOs.GameDAO;
import cs340.game.server.DB.ServerGameState;
import cs340.game.server.Factories.DAOFactory;
import cs340.game.shared.CommonData;
import cs340.game.shared.data.Data;

public class CommandHelper {

    public static void updateGame(DAOFactory daoFactory, ServerGameState game, Data data) {

        CommandDAO commandDAO = daoFactory.getCommandDAO();
        String gameName = game.getGameState().getGameName();
        if(commandDAO.getNextSequenceNumber(gameName)>CommonData.COMMANDS_BETWEEN_CHECKPOINTS) {

            commandDAO.clearCommandsForGame(gameName);
            GameDAO gameDAO = daoFactory.getGameDAO();
            gameDAO.updateGame(gameName, game);
        }else {

            commandDAO.addCommand(gameName, data);
        }
    }
}
