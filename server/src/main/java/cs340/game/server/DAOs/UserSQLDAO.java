package cs340.game.server.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import cs340.game.server.DB.SQLiteConnectionProxy;
import cs340.game.shared.models.User;

public class UserSQLDAO implements UserDAO{

    @Override
    public void addUser(String userName, String password) {
        String authToken = UUID.randomUUID().toString();
        try {
            PreparedStatement stmt = null;
            try {
                String insertUserStr = "INSERT INTO User (username, password, authToken) " +
                        "VALUES (?,?,?)";
                stmt = SQLiteConnectionProxy.getConn().prepareStatement(insertUserStr);

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
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean containsUser(User user) {
        return (getUserByUsername(user.getUsername()).getPassword() == user.getPassword());
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
                stmt = SQLiteConnectionProxy.getConn().prepareStatement(getUserStr);
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
            }
            return resultUser;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }

    public void clearUserTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.getConn().createStatement();

                stmt.executeUpdate("DELETE FROM User");
            }
            finally {
                if(stmt != null) {
                    stmt.close();
                }
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createUserTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.getConn().createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS User ( username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "authToken TEXT NOT NULL UNIQUE)");
            }
            finally {
                if(stmt != null) {
                    stmt.close();
                }
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
