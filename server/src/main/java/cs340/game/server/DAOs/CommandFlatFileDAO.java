package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.data.Data;

public class CommandFlatFileDAO extends FlatFileDAO implements CommandDAO{
    private static final String filename = "command.fdb";

    private static CommandFlatFileDAO instance;

    private CommandFlatFileDAO(){

    }

    public static CommandFlatFileDAO getInstance(){
        if (instance == null){
            instance = new CommandFlatFileDAO();
        }
        return instance;
    }

    @Override
    public void addCommand(String gameName, Data data) {

    }

    @Override
    public void clearCommandsForGame(String gameName) {

    }

    @Override
    public ArrayList<Data> getCommandsForGame(String gameName) {
        return null;
    }

    @Override
    public int getNextSequenceNumber(String gameName) {
        return 0;
    }

    @Override
    protected boolean updateDB() {
        return false;
    }

    @Override
    protected boolean loadDB() {
        return false;
    }

    @Override
    public void clearData() {

    }
}
