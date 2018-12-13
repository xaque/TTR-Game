package cs340.game.server.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import cs340.game.server.DB.SQLiteConnectionProxy;
import cs340.game.shared.models.User;

public class UserSQLDAO implements UserDAO{

    private static UserSQLDAO instance;

    private UserSQLDAO() {
        createUserTable();
    }

    public static UserSQLDAO getInstance() {
        if(instance == null) {
            instance = new UserSQLDAO();
        }
        return instance;
    }

    @Override
    public void addUser(String userName, String password) {
        String authToken = UUID.randomUUID().toString();
        try {
            PreparedStatement stmt = null;
            try {
                String insertUserStr = "INSERT INTO User (username, password, authToken) " +
                        "VALUES (?,?,?)";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(insertUserStr);

                stmt.setString(1, userName);
                stmt.setString(2, password);
                stmt.setString(3, authToken);
                if(stmt.executeUpdate() != 1) {
                    System.out.println("addUser failed.");
                }
            }
            finally {
                if(stmt != null) {
                    stmt.close();
                }
                SQLiteConnectionProxy.closeConnection(true);
            }
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
        }
    }

    @Override
    public boolean containsUser(User user) {

        if(!containsUsername(user.getUsername())){
            return false;
        }

        User dbUser = getUserByUsername(user.getUsername());
        return (dbUser.getPassword().equals(user.getPassword()));
    }

    @Override
    public boolean containsUsername(String username) {
        return (getUserByUsername(username) != null);
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            User resultUser = null;
            try {
                String getUserStr = "SELECT * FROM User WHERE username=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getUserStr);
                stmt.setString(1, username);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    resultUser = new User(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                SQLiteConnectionProxy.closeConnection(true);
            }
            return resultUser;
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUsernameByAuthToken(String authToken) {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String username = null;
            try {
                String getUsernameStr = "SELECT * FROM User WHERE authToken=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getUsernameStr);
                stmt.setString(1, authToken);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    username = rs.getString(1);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                SQLiteConnectionProxy.closeConnection(true);
            }
            return username;
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }

    @Override
    public void clearData() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();

                stmt.executeUpdate("DELETE FROM User");
            }
            finally {
                if(stmt != null) {
                    stmt.close();
                }
                SQLiteConnectionProxy.closeConnection(true);
            }
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
        }
    }

    public String getGameNameByUsername(String username) {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String gameName = null;
            try {
                String getGameNameStr = "SELECT * FROM User WHERE username=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getGameNameStr);
                stmt.setString(1, username);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    gameName = rs.getString(4);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                SQLiteConnectionProxy.closeConnection(true);
            }
            return gameName;
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    public String getGameNameByAuthToken(String authToken) {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String gameName = null;
            try {
                String getGameNameStr = "SELECT * FROM User WHERE authToken=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getGameNameStr);
                stmt.setString(1, authToken);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    gameName = rs.getString(4);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                SQLiteConnectionProxy.closeConnection(true);
            }
            return gameName;
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    public void createUserTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS User ( username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "authToken TEXT NOT NULL UNIQUE," +
                "gameName TEXT)");
            }
            finally {
                if(stmt != null) {
                    stmt.close();
                }
                SQLiteConnectionProxy.closeConnection(true);
            }
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
        }
    }
}
