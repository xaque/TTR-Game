package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.data.Data;

public interface CommandDAO {

    public void addCommand(String gameName, Data data);
    public void clearCommandsForGame(String gameName);
    public ArrayList<Data> getCommandsForGame(String gameName);
}
