package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.dbConnection.DBConnection;

public class LoginService {

    // Returns true if login successful, false otherwise
    public boolean loginService(String username, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if a row exists
        }
    }
}

