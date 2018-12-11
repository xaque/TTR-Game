package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.models.User;

public class UserFlatFileDAO implements UserDAO{

    @Override
    public void addUser(String userName, String password) {

    }

    @Override
    public boolean containsUser(User user) {
        return false;
    }

    @Override
    public boolean containsUsername(String username) {
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }
}
