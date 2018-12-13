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
                String insertGameStr = "INSERT INTO LobbyGame (name, gameStarted, playerNumber, player1) " +
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
                try {
                    String addPlayerString = "UPDATE LobbyGame SET " + nameOfSQLColumn + "=?, playerNumber=? WHERE name=?";
                    stmt = SQLiteConnectionProxy.openConnection().prepareStatement(addPlayerString);
                    stmt.setString(1, username);
                    stmt.setInt(2, numberOfPlayers + 1);
                    stmt.setString(3, game.getName());

                    if (stmt.executeUpdate() != 1) {
                        System.out.println("addPlayerToGame failed.");
                    }
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
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
                    for(int i = 4; i < 9; i++) {
                        if(rs.getString(i) != null) {
                            playerNames.add(rs.getString(i)); // this loop may cause a problem if resultSet needs to be read in sequential order
                        }
                    }
                    boolean gameStarted;
                    if(rs.getInt(2) == 0){
                        gameStarted = false;
                    }else{
                        gameStarted = true;
                    }

                    resultGame = new Game(rs.getString(1),
                            gameStarted,
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
    public ArrayList<Game> getAllGames() {

        ArrayList<Game> games = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String getUserStr = "SELECT * FROM LobbyGame";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getUserStr);

                rs = stmt.executeQuery();
                while(rs.next()) {
                    ArrayList<String> playerNames = new ArrayList<>();
                    for(int i = 4; i < 9; i++) {
                        if(rs.getString(i) != null) {
                            playerNames.add(rs.getString(i)); // this loop may cause a problem if resultSet needs to be read in sequential order
                        }
                    }
                    boolean gameStarted;
                    if(rs.getInt(2) == 0){
                        gameStarted = false;
                    }else{
                        gameStarted = true;
                    }
                    Game rowGame = new Game(rs.getString(1),
                            gameStarted,
                            playerNames);
                    games.add(rowGame);
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
            return games;
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

    public void dropLobbyTable(){
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();

                stmt.executeUpdate("DROP TABLE LobbyGame");
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
    public void clearData() {
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
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LobbyGame ( name TEXT PRIMARY KEY NOT NULL UNIQUE," +
                        "gameStarted INTEGER NOT NULL," +
                        "playerNumber INTEGER NOT NULL," +
                        "player1 TEXT NOT NULL UNIQUE," +
                        "player2 TEXT UNIQUE," +
                        "player3 TEXT UNIQUE," +
                        "player4 TEXT UNIQUE," +
                        "player5 TEXT UNIQUE," +
                        "CHECK(gameStarted IN (0,1)))");
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
