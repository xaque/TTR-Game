package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.models.User;

public interface UserDAO {
    public void addUser(String userName, String password);
    public boolean containsUser(User user);
    public boolean containsUsername(String username);
    public User getUserByUsername(String username);
    public String getUsernameByAuthToken(String authToken);
    public ArrayList<User> getAllUsers();
    public void clearData();
}
