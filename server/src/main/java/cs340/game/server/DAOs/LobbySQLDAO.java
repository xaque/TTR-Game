package cs340.game.server.DAOs;

import java.sql.*;
import java.util.ArrayList;

import cs340.game.server.DB.SQLiteConnectionProxy;
import cs340.game.shared.models.Game;

public class LobbySQLDAO implements LobbyDAO {

    private static LobbySQLDAO instance;

    private LobbySQLDAO() {
        createLobbyTable();
    }

    public static LobbySQLDAO getInstance() {
        if(instance == null) {
            instance = new LobbySQLDAO();
        }
        return instance;
    }

    @Override
    public void addGame(Game game) {
        try {
            PreparedStatement stmt = null;
            try {
                String insertGameStr = "INSERT INTO User (name, gameStarted, playerNumber, player1) " +
                        "VALUES (?,0,1,?)";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(insertGameStr);

                stmt.setString(1, game.getName());
                stmt.setString(2, game.getPlayers().get(0));
                if(stmt.executeUpdate() != 1) {
                    System.out.println("addGame failed.");
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

    public Integer getNumberOfPlayersInGame(Game game) {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String gameName = game.getName();
            try {
                String getUserStr = "SELECT * FROM LobbyGame WHERE name=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getUserStr);
                stmt.setString(1, gameName);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(3);
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
            return null;
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void addPlayerToGame(String username, Game game) {
        try {
            PreparedStatement stmt = null;
            int numberOfPlayers = getNumberOfPlayersInGame(game);
            String nameOfSQLColumn = "player" + Integer.toString(numberOfPlayers + 1);
            try {
                String addPlayerString = "UPDATE LobbyGame SET ?=? WHERE name=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(addPlayerString);
                stmt.setString(1, nameOfSQLColumn);
                stmt.setString(2, username);
                stmt.setString(3, game.getName());

                if(stmt.executeUpdate() != 1) {
                    System.out.println("addPlayerToGame failed.");
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
    public Game getGame(String gameName) {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Game resultGame = null;
            ArrayList<String> playerNames = new ArrayList<>();
            try {
                String getUserStr = "SELECT * FROM LobbyGame WHERE name=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getUserStr);
                stmt.setString(1, gameName);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    for(int i = 3; i < 8; i++) {
                        if(rs.getString(i) != null) {
                            playerNames.add(rs.getString(i)); // this loop may cause a problem if resultSet needs to be read in sequential order
                        }
                    }
                    resultGame = new Game(rs.getString(1),
                            rs.getBoolean(2), //this line may have a problem, as SQLite actually stores an int, not a boolean
                            playerNames);
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
            return resultGame;
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void startGame(Game game) {
        try {
            PreparedStatement stmt = null;
            try {
                String startGameString = "UPDATE LobbyGame SET gameStarted=1 WHERE name=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(startGameString);
                stmt.setString(1, game.getName());

                if(stmt.executeUpdate() != 1) {
                    System.out.println("startGame failed.");
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

    public void clearLobbyTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();

                stmt.executeUpdate("DELETE FROM LobbyGame");
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

    public void createLobbyTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LobbyGame ( name TEXT NOT NULL UNIQUE," +
                        "gameStarted INTEGER NOT NULL CHECK(gameStarted = 0 OR gameStarted = 1)," +
                        "playerNumber INTEGER NOT NULL" +
                        "player1 TEXT NOT NULL UNIQUE," +
                        "player2 TEXT UNIQUE," +
                        "player3 TEXT UNIQUE," +
                        "player4 TEXT UNIQUE," +
                        "player5 TEXT UNIQUE)");
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
