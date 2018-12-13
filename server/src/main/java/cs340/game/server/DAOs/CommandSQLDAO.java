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

        int nextSequenceNumber = getNextSequenceNumber(gameName);

        try {
            //fbyte[] decodedByte = Base64.decode(serializedData, 0);
            //Blob blobData = new SerialBlob(decodedByte);

            PreparedStatement stmt = null;
            try {
                try {
                    String insertUserStr = "INSERT INTO Commands (gameName, sequenceNumber, commandData) " +
                            "VALUES (?,?,?)";
                    stmt = SQLiteConnectionProxy.openConnection().prepareStatement(insertUserStr);

                    stmt.setString(1, gameName);
                    stmt.setInt(2, nextSequenceNumber);
                    stmt.setString(3, serializedData); // Our JCDB driver does not support setBlob
                    if (stmt.executeUpdate() != 1) {
                        System.out.println("addUser failed.");
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

                rs = stmt.executeQuery();
                if (rs.next()) {
                    /*SerialBlob blobResult = (SerialBlob)(rs.getBlob(2));
                    InputStream in = blobResult.getBinaryStream();
                    byte[] array = new byte[in.available()];
                    in.read(array);
                    String s = array.toString();*/
                    String s = rs.getString(3);
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

            }
            SQLiteConnectionProxy.closeConnection(true);
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

    public void dropTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();

                stmt.executeUpdate("DROP TABLE Commands");
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
        finally {
            dropTable();
        }
    }

    public void createCommandTable() {
        try {
            Statement stmt = null;
            try {
                stmt = SQLiteConnectionProxy.openConnection().createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Commands ( gameName TEXT NOT NULL," +
                        "sequenceNumber INTEGER NOT NULL," +
                        "commandData BLOB NOT NULL)");
                        //"PRIMARY KEY(gameName, sequenceNumber) )");
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
