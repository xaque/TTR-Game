package cs340.game.server.DAOs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import cs340.game.server.DB.SQLiteConnectionProxy;
import cs340.game.server.DB.ServerGameState;
import cs340.game.shared.Base64;
import cs340.game.shared.Serializer;

public class GameSQLDAO implements GameDAO{

    private static GameSQLDAO instance;

    private GameSQLDAO() {
        createGameTable();
    }

    public static GameSQLDAO getInstance() {
        if(instance == null) {
            instance = new GameSQLDAO();
        }
        return instance;
    }

    @Override
    public void addGame(String gameName, ServerGameState game) {
        String serializedGame = "";
        try {
            serializedGame = Serializer.serializeObject(game);
        }catch (IOException ex){
            ex.printStackTrace();
        }

        try {
            //byte[] decodedByte = Base64.decode(serializedGame, 0);
            //Blob blobData = new SerialBlob(decodedByte);
            PreparedStatement stmt = null;
            try {
                String addGameStr = "INSERT INTO ActiveGame (name, gameState) " +
                        "VALUES (?,?)";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(addGameStr);

                stmt.setString(1, gameName);
                //stmt.setBlob(2, blobData);
                stmt.setString(2, serializedGame);
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

    @Override
    public void updateGame(String gameName, ServerGameState game) {

    }

    public ServerGameState getGame(String gameName) {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            ServerGameState resultGame = null;
            try {
                String getGameStr = "SELECT * FROM ActiveGame WHERE name=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getGameStr);
                stmt.setString(1, gameName);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    /*SerialBlob blobResult = (SerialBlob)(rs.getBlob(2));
                    InputStream in = blobResult.getBinaryStream();
                    byte[] array = new byte[in.available()];
                    in.read(array);
                    String s = array.toString();
                    resultGame = Serializer.deserializeObject(s, ServerGameState.class);*/
                    //byte[] blob = rs.getBytes(2);
                    //String s = new String(blob);
                    String s = rs.getString(2);
                    resultGame = Serializer.deserializeObject(s, ServerGameState.class);
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
        catch(SQLException | ClassNotFoundException | IOException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ServerGameState getGameByUsername(String username) {
        String gameName = UserSQLDAO.getInstance().getGameNameByUsername(username);
        return getGame(gameName);
    }

    @Override
    public ServerGameState getGameByAuthToken(String authToken) {
        String gameName = UserSQLDAO.getInstance().getGameNameByAuthToken(authToken);
        return getGame(gameName);
    }

    @Override
    public ArrayList<ServerGameState> getAllGames() {
        ArrayList<ServerGameState> gameList = new ArrayList<>();
        try {
            Statement stmt = null;
            ResultSet rs = null;
            ServerGameState resultGame = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();
                rs = stmt.executeQuery("SELECT * FROM ActiveGame");
                if (rs.next()) {
                    /*SerialBlob blobResult = (SerialBlob)(rs.getBlob(2));
                    InputStream in = blobResult.getBinaryStream();
                    byte[] array = new byte[in.available()];
                    in.read(array);
                    String s = array.toString();*/
                    //byte[] blobResult = rs.getBytes(2);
                    //String s = new String(blobResult);
                    String s = rs.getString(2);
                    resultGame = Serializer.deserializeObject(s, ServerGameState.class);
                    gameList.add(resultGame);
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
            return gameList;
        }
        catch(SQLException | ClassNotFoundException | IOException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    public void createGameTable() {
        try {
            Statement stmt = null;
            try {
                SQLiteConnectionProxy.openConnection();
                stmt = SQLiteConnectionProxy.openConnection().createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ActiveGame ( name TEXT NOT NULL UNIQUE," +
                        "gameState BLOB NOT NULL)");
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

                stmt.executeUpdate("DELETE FROM ActiveGame");
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
        finally {
            dropTable();
        }
    }

    public void dropTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();

                stmt.executeUpdate("DROP TABLE ActiveGame");
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
