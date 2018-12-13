package cs340.game.server.DAOs;

import java.util.ArrayList;
import java.util.HashMap;

import cs340.game.shared.data.Data;

public class CommandFlatFileDAO extends FlatFileDAO implements CommandDAO{
    private static final String filename = "command.fdb";
    HashMap<String,ArrayList<Data>> gamesCommands = new HashMap<>();

    private static CommandFlatFileDAO instance;

    private CommandFlatFileDAO(){
        if (!loadDB()){
            gamesCommands = new HashMap<>();
        }
    }

    public static CommandFlatFileDAO getInstance(){
        if (instance == null){
            instance = new CommandFlatFileDAO();
        }
        return instance;
    }

    @Override
    public void addCommand(String gameName, Data data) {
        ArrayList<Data> commands = gamesCommands.get(gameName);
        commands.add(data);
        gamesCommands.put(gameName, commands);
        updateDB();
    }

    @Override
    public void clearCommandsForGame(String gameName) {
        gamesCommands.put(gameName, new ArrayList<Data>());
        updateDB();
    }

    @Override
    public ArrayList<Data> getCommandsForGame(String gameName) {
        return gamesCommands.get(gameName);
    }

    @Override
    public int getNextSequenceNumber(String gameName) {
        //TODO is the next sequence number always sequential like this?
        return gamesCommands.get(gameName).size();
    }

    @Override
    protected boolean updateDB() {
        return super.writeObjectToFile(filename, gamesCommands);
    }

    @Override
    protected boolean loadDB() {
        gamesCommands = super.readObjectFromFile(filename, gamesCommands.getClass());
        if (gamesCommands == null){
            return false;
        }
        return true;
    }

    @Override
    public void clearData() {
        super.deleteFile(filename);
        gamesCommands = new HashMap<>();
    }
}
