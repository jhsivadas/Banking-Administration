package dbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/* This class establishes a connection to the database */
public class dbConnection {

    private static final String SQLCONN = "jdbc:sqlite:bankingdb.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQLCONN);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
