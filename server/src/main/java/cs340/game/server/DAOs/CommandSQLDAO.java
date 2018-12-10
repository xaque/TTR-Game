package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.data.Data;

public class CommandSQLDAO implements CommandDAO{

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
}
