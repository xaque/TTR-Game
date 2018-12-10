package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.models.User;

public interface UserDAO {

    public void registerUser(String userName, String password);
    public void checkForUser(String userName);
    public void validateLogin(String userName, String password);
    public ArrayList<User> getAllUsers();
}
