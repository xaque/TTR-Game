package cs340.game.server.DAOs;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.SQLiteConnectionProxy;
import cs340.game.shared.Base64;
import cs340.game.shared.Serializer;
import cs340.game.shared.data.Data;

public class CommandSQLDAO implements CommandDAO{

    private static CommandSQLDAO instance;

    private CommandSQLDAO() {
        createCommandTable();
    }

    public static CommandSQLDAO getInstance() {
        if(instance == null) {
            instance = new CommandSQLDAO();
        }
        return instance;
    }

    @Override
    public void addCommand(String gameName, Data data) {

        String serializedData = "";
        try {
            serializedData = Serializer.serializeData(data);
        }catch (IOException ex){
            ex.printStackTrace();
        }

        try {
            byte[] decodedByte = Base64.decode(serializedData, 0);
            Blob blobData = new SerialBlob(decodedByte);

            PreparedStatement stmt = null;
            try {
                String insertUserStr = "INSERT INTO Commands (gameName, sequenceNumber, commandData) " +
                        "VALUES (?,?,?)";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(insertUserStr);

                stmt.setString(1, gameName);
                stmt.setInt(2, getNextSequenceNumber(gameName));
                stmt.setBlob(3, blobData);
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
    public void clearCommandsForGame(String gameName) {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();

                stmt.executeUpdate("DELETE FROM Commands WHERE gameName=" + "'" + gameName + "'");
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
    public ArrayList<Data> getCommandsForGame(String gameName) {
        ArrayList<Data> commandList = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Data resultCommand = null;
            try {
                String getCommandsStr = "SELECT * FROM Commands WHERE gameName=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getCommandsStr);
                stmt.setString(1, gameName);
                if(stmt.executeUpdate() != 1) {
                    System.out.println("getCommandsForGame failed.");
                }
                if (rs.next()) {
                    SerialBlob blobResult = (SerialBlob)(rs.getBlob(2));
                    InputStream in = blobResult.getBinaryStream();
                    byte[] array = new byte[in.available()];
                    in.read(array);
                    String s = array.toString();
                    resultCommand = Serializer.deserializeObject(s, Data.class);
                    commandList.add(resultCommand);
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
            return commandList;
        }
        catch(ClassNotFoundException | IOException | SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public int getNextSequenceNumber(String gameName) {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int nextSequenceNumber = 0;
            int maxSequenceNumber = 0;
            try {
                String getUserStr = "SELECT MAX(sequenceNumber) FROM Commands WHERE gameName=?";
                stmt = SQLiteConnectionProxy.openConnection().prepareStatement(getUserStr);
                stmt.setString(1, gameName);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    maxSequenceNumber = rs.getInt(1);
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
            nextSequenceNumber = maxSequenceNumber + 1;
            return nextSequenceNumber;
        }
        catch(SQLException ex) {
            SQLiteConnectionProxy.closeConnection(false);
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public void clearData() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();

                stmt.executeUpdate("DELETE FROM Commands");
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

    public void createCommandTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Commands ( gameName TEXT NOT NULL UNIQUE," +
                        "sequenceNumber INTEGER NOT NULL," +
                        "commandData BLOB NOT NULL)");
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
