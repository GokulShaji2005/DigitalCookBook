package dao.userDao;

import java.sql.*;

/**
 * Author: Amy Anup
 * Date: 14/10/2025
 * 
 * UserDAO handles database operations on the 'user' table.
 */
public class UserDAO {

    /**
     * Validate a user's login credentials.
     * 
     * @param username the username input
     * @param password the password input
     * @return true if a matching user exists in the database, false otherwise
     */
    public boolean validateUser(String username, String password) {
        boolean valid = false;
        String query = "SELECT * FROM user WHERE username=? AND password=?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                valid = rs.next(); // true if a row exists
            }

        } catch (Exception e) {
            System.out.println("Error validating user: " + e.getMessage());
        }

        return valid;
    }

    /**
     * Fetch all users and print to console (optional test method).
     */
    public void getAllUsers() {
        String query = "SELECT * FROM user";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Users in database:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "  " + rs.getString("username") + "  " + rs.getString("password"));
            }

        } catch (Exception e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
    }

    /**
     * Main method for quick testing.
     */
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        dao.getAllUsers(); // Test fetching all users

        System.out.println("\nLogin test:");
        System.out.println("Honey/honey123 -> " + dao.validateUser("Honey", "honey123"));
        System.out.println("Honey/wrongpass -> " + dao.validateUser("Honey", "wrongpass"));
    }
}
