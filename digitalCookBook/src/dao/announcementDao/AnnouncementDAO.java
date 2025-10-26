package dao.announcementDao;

import model.Announcements;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.dbConnection.DBConnection;

public class AnnouncementDAO {

    public void createAnnouncement(Announcements a) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO announcements (title, message, target_audience) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, a.getTitle());
        ps.setString(2, a.getMessage());
        ps.setString(3, a.getTargetAudience());
      
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    
    public void deleteAnnouncement(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM announcements WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public List<Announcements> getAnnouncementsForRole(String role) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM announcements WHERE target_audience=? OR target_audience='Both' ORDER BY created_at DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, role);
        ResultSet rs = ps.executeQuery();

        List<Announcements> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Announcements(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("message"),
                rs.getString("target_audience"),
            
                rs.getTimestamp("created_at")
            ));
        }
        rs.close();
        ps.close();
        conn.close();
        return list;
    }
    
    public List<Announcements> getAllAnnouncements() throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM announcements ORDER BY created_at DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Announcements> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Announcements(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("message"),
                rs.getString("target_audience"),
             
                rs.getTimestamp("created_at")
            ));
        }
        rs.close();
        ps.close();
        conn.close();
        return list;
    }

}
