/**
 * Author: Amy Anup
 * Date: 14/10/2025
 * 
 * UserDAO handles database operations on the 'user' table.
 */



/**
 * UserDAO handles database operations on the 'user' table.
 * Supports fetching users by role: Chef or Viewer.
 */
package dao.userDao;
import dao.dbConnection.DBConnection;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 🔹 Fetch all Chefs
    public List<User> getChefs() throws ClassNotFoundException {
        List<User> chefs = new ArrayList<>();
        String query = "SELECT id, username, role FROM user WHERE role = 'Chef'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                chefs.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching chefs: " + e.getMessage());
        }

        return chefs;
    }

    // 🔹 Fetch all Viewers
    public List<User> getViewers() throws ClassNotFoundException {
        List<User> viewers = new ArrayList<>();
        String query = "SELECT id, username, role FROM user WHERE role = 'Viewer'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                viewers.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching viewers: " + e.getMessage());
        }

        return viewers;
    }
}


   
    /**
     * Main method for testing
     */
//    public static void main(String[] args) {
//        UserDAO dao = new UserDAO();
//
//        System.out.println("All Users:");
//        dao.getAllUsers();
//
//        System.out.println("\nChefs:");
//        dao.getChefs();
//
//        System.out.println("\nViewers:");
//        dao.getViewers();
//
//        
//    }

