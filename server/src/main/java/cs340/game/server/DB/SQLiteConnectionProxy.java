package cs340.game.server.DB;

import java.sql.*;

/**
 * Created by Stephen on 12/10/2018.
 */

public class SQLiteConnectionProxy {
    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection conn;

    private SQLiteConnectionProxy() {
        return;
    }

    public Connection openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:TicketToRideServer.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
            return conn;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        return conn;
    }
}
