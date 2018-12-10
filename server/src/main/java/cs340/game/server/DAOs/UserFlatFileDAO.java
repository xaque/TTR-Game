package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.models.User;

public class UserFlatFileDAO implements UserDAO{

    @Override
    public void registerUser(String userName, String password) {

    }

    @Override
    public void checkForUser(String userName) {

    }

    @Override
    public void validateLogin(String userName, String password) {

    }

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }
}
