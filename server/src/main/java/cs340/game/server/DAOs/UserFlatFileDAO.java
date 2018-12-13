package cs340.game.server.DAOs;

import java.util.ArrayList;
import java.util.UUID;

import cs340.game.shared.models.User;

public class UserFlatFileDAO extends FlatFileDAO implements UserDAO{
    private static final String filename = "users.fdb";
    private ArrayList<User> users = new ArrayList<>();

    private static UserFlatFileDAO instance;

    private UserFlatFileDAO(){
        if (!loadDB()){
            users = new ArrayList<>();
        }
    }

    public static UserFlatFileDAO getInstance(){
        if (instance == null){
            instance = new UserFlatFileDAO();
        }
        return instance;
    }

    @Override
    public void addUser(String userName, String password) {
        User u = new User(userName, password);
        u.setAuthToken(UUID.randomUUID().toString());
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
    public String getUsernameByAuthToken(String authToken) {
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
        try {
            users = super.readObjectFromFile(filename, users.getClass());
        }catch (Exception e){
            return false;
        }

        if (users == null){
            return false;
        }
        return true;
    }

    @Override
    public void clearData() {
        users = new ArrayList<>();
        deleteFile(filename);
    }

    public String getGameNameByAuthToken(String authToken) {
        for (User u : users){
            if (u.getAuthToken().equals(authToken)){
                return getGameNameByUsername(u.getUsername());
            }
        }
        return null;
    }

    public String getGameNameByUsername(String username){
        //TODO not sure if this is actually used. Just copying it from the UserSQLDAO
        return null;
    }
}
