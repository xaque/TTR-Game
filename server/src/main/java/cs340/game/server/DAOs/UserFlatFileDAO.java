package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.models.User;

public class UserFlatFileDAO extends FlatFileDAO implements UserDAO{
    private static final String filename = "users.fdb";
    private ArrayList<User> users;

    public UserFlatFileDAO(){
        if (!loadDB()){
            users = new ArrayList<>();
        }
    }

    @Override
    public void addUser(String userName, String password) {
        users.add(new User(userName, password));
        updateDB();
    }

    @Override
    public boolean containsUser(User user) {
        return users.contains(user);
    }

    @Override
    public boolean containsUsername(String username) {
        for (User u : users){
            if (u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        for (User u : users){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return users;
    }

    @Override
    protected boolean updateDB(){
        return super.writeObjectToFile(filename, users);
    }

    @Override
    protected boolean loadDB(){
        users = super.readObjectFromFile(filename, users.getClass());
        if (users == null){
            return false;
        }
        return true;
    }
}
