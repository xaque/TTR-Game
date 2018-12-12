package cs340.game.server;

import java.util.ArrayList;

import cs340.game.server.DAOs.CommandDAO;
import cs340.game.server.DAOs.GameDAO;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.server.Factories.DAOFactory;
import cs340.game.server.Factories.FlatFileDAOFactory;
import cs340.game.server.Factories.InMemoryDAOFactory;
import cs340.game.server.Factories.SQLDAOFactory;
import cs340.game.server.Handlers.GenericHandler;
import cs340.game.shared.CommandData;
import cs340.game.shared.CommonData;
import cs340.game.shared.data.Data;

public class Main {
    public static void main(String[] args){

        if(args.length > 0) {
            CommonData.HOSTNAME = args[0];
        }

        if(args.length == 4){
            CommonData.PERSISTENCE_TYPE = args[2];
            CommonData.COMMANDS_BETWEEN_CHECKPOINTS = Integer.parseInt(args[3]);
        }

        loadAllGameData();

        new ServerCommunicator().run();
    }

    private static void loadAllGameData(){

        DAOFactory daoFactory = getDAOFactory();
        GameDAO gameDAO = daoFactory.getGameDAO();
        CommandDAO commandDAO = daoFactory.getCommandDAO();

        ArrayList<ServerGameState> games = gameDAO.getAllGames();
        GenericHandler handler = new GenericHandler();

        for(int i = 0; i < games.size(); i++) {
            ActiveGamesDatabase.getInstance().addGame(games.get(i));

            String gameName = games.get(i).getGameState().getGameName();
            ArrayList<Data> commandsToRun = commandDAO.getCommandsForGame(gameName);
            for(int j = 0; j < commandsToRun.size(); j++){
                handler.runCommand(commandsToRun.get(j));
            }

            commandDAO.clearCommandsForGame(gameName);
        }
    }

    private static DAOFactory getDAOFactory(){
        switch (CommonData.PERSISTENCE_TYPE){
            case "sqlite":
                return new SQLDAOFactory();
            case "file":
                return new FlatFileDAOFactory();
            case "memory":
                return new InMemoryDAOFactory();
            default:
                return new InMemoryDAOFactory();
        }
    }
}
