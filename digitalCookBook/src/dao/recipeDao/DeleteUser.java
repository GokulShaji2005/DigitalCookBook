package dao.recipeDao;

import java.sql.*;
import dao.dbConnection.DBConnection;

public class DeleteUser {
    private Connection conn;

    // 🔹 Constructor — runs when object is created
    public DeleteUser() throws SQLException, ClassNotFoundException {
        this.conn = DBConnection.getConnection();
        System.out.println("✅ Connection established from constructor!");
    }

    // 🔹 Method — performs deletion
    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";

        try (
        	PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            int rows = stmt.executeUpdate();

            if (rows > 0)
                System.out.println("✅ User '" + username + "' deleted successfully!");
            else
                System.out.println("⚠️ No user found with username: " + username);
        }
    }

    public static void main(String[] args) {
        try {
            DeleteUser du = new DeleteUser(); // constructor called
            du.deleteUser("gokul@2005");        // method called
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error deleting user: " + e.getMessage());
        }
    }
}
