package dao.userDao;

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
    public void deleteUser(int id,String username) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";

        try (
        	PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0)
                System.out.println("✅ User '" + username + "' deleted successfully!");
            else
                System.out.println("⚠️ No user found with username: " + username);
        }
    }

}
