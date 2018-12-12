package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.data.Data;

public class CommandFlatFileDAO extends FlatFileDAO implements CommandDAO{
    private static final String name_prefix = "command";
    private static final String name_extension = "fdb";
    private String filename;

    public CommandFlatFileDAO(){

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
}
