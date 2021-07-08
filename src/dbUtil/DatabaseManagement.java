package dbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/* This class contains all the methods to update and query the database
containing all the user banking information. */
public class DatabaseManagement {

    public DatabaseManagement() {

    }

    // Returns connection to database
    private Connection connect() {
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (conn == null) {
                System.exit(1);
            }
        return conn;
    }

    // Inserts new user into database.
    public void insert(String name, double balance) {
        String sql = "INSERT INTO CustomerList(name, balance) VALUES(?, ?)";

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql); 
            pstmt.setString(1, name);
            pstmt.setDouble(2, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Retrieves a user ID given their full name.
    public int retrieveID(String name) {
        String sql = "SELECT personId FROM CustomerList WHERE name = ?";
        int id = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) { 
                id = rs.getInt("personId");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    // Retrieves a user name given their ID.
    public String retrieveName(int ID) {
        String sql = "SELECT name FROM CustomerList WHERE personID = ?";
        String name = "";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) { 
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return name;
    }

    // Retrieves the user's balance given their ID.
    public int retrieveBalance(int ID) {
        String sql = "SELECT balance FROM CustomerList WHERE personID = ?";
        int balance = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                balance = rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    // Updates the user balance given withdraw/deposit amount.
    public void updateBalance(int id, int amount) {
        String sql = "UPDATE CustomerList SET balance = ? WHERE personId = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, retrieveBalance(id) + amount);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // Used to create table (Admin only).
    private void createTable() {
        String sql = "CREATE TABLE CustomerList ("
        + "personID INT AUTO_INCREMENT,"
        + "name VARCHAR(45) NOT NULL,"
        + "balance INT NOT NULL,"
        + "PRIMARY KEY (personID))";

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
