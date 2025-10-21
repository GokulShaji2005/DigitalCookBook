package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dao.dbConnection.DBConnection;

public class SignUpService {

    public boolean signUpService(String username, String password, String role) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0; // returns true if insertion was successful
        }
    }
}

