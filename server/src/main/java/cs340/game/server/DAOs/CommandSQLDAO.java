package cs340.game.server.DAOs;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import cs340.game.server.DB.SQLiteConnectionProxy;
import cs340.game.shared.data.Data;

public class CommandSQLDAO implements CommandDAO{

    @Override
    public void addCommand(String gameName, Data data) {

        // TODO serialized data
        String serializedData = "";

        try {
            // TODO create BLOB correctly
            byte[] byteArray = serializedData.getBytes("UTF-8");
            Blob blobData = null;
            blobData.setBytes(1, byteArray);

            PreparedStatement stmt = null;
            try {
                String insertUserStr = "INSERT INTO Commands (gameName, sequenceNumber, commandData) " +
                        "VALUES (?,?,?)";
                stmt = SQLiteConnectionProxy.getConn().prepareStatement(insertUserStr);

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
            }
        }
        catch(SQLException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void clearCommandsForGame(String gameName) {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.getConn().createStatement();

                stmt.executeUpdate("DELETE FROM Commands WHERE gameName=" + "'" + gameName + "'");
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
    public ArrayList<Data> getCommandsForGame(String gameName) {
        return null;
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
                stmt = SQLiteConnectionProxy.getConn().prepareStatement(getUserStr);
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
            }
            nextSequenceNumber = maxSequenceNumber + 1;
            return nextSequenceNumber;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public void clearCommandTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.getConn().createStatement();

                stmt.executeUpdate("DELETE FROM Commands");
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

    public void createCommandTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.getConn().createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Commands ( gameName TEXT NOT NULL UNIQUE," +
                        "sequenceNumber INTEGER NOT NULL UNIQUE," +
                        "commandData BLOB NOT NULL)");
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
