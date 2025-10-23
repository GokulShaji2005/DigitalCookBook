package dao.recipeDao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import dao.dbConnection.DBConnection;

public class FavouriteDao {

    // ✅ Add to favorites
	public boolean addToFavorites(int userId, int recipeId) {
	    if (isFavorite(userId, recipeId)) return false; // Already exists

	    String sql = "INSERT INTO favourite (user_id, recipe_id) VALUES (?, ?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, userId);
	        stmt.setInt(2, recipeId);
	        int rows = stmt.executeUpdate();
	        return rows > 0;
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


    // ✅ Remove from favorites
	public boolean removeFromFavorites(int userId, int recipeId) {
	    String sql = "DELETE FROM favourite WHERE user_id = ? AND recipe_id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, userId);
	        stmt.setInt(2, recipeId);
	        int rows = stmt.executeUpdate();
	        return rows > 0;
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


    // ✅ Check if already favorite
    public boolean isFavorite(int userId, int recipeId) {
        String sql = "SELECT 1 FROM favourite WHERE user_id = ? AND recipe_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, recipeId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

   


 
    public  List<Integer> getFavouriteRecipeIds(int userId) throws SQLException, ClassNotFoundException {
        List<Integer> favIds = new ArrayList<>();
        String sql = "SELECT recipe_id FROM favourite WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                favIds.add(rs.getInt("recipe_id"));
            }
        }
        return favIds;
    }

 
  

}

