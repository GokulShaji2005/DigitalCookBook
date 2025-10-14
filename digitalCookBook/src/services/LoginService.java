package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.dbConnection.DBConnection;
public class LoginService {
	 
    public String loginService(String username, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT role FROM users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);   
            ps.setString(2, password);   

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role"); 
            } else {
                return null; 
            }
        }
    }
}
