package dao.recipeDao;

import model.Recipe;
import java.sql.*;
import java.util.*;

import dao.dbConnection.DBConnection;

public class RecipeCategoryDao {

    // ✅ Get recipes by category (Veg / Non-Veg / Dessert)
    public List<Recipe> getRecipesByCategory(String category) throws ClassNotFoundException {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipe WHERE category = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Recipe r = new Recipe(
                    rs.getInt("recipe_id"),
                    rs.getString("title"),
                    rs.getString("ingredients"),
                    rs.getString("instructions"),
                    rs.getString("category"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("created_at")
                );
                list.add(r);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching recipes by category: " + e.getMessage());
        }

        return list;
    }

    // ✅ Search recipes by keyword (title or ingredient)
    public List<Recipe> searchRecipes(String keyword) throws ClassNotFoundException {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipe WHERE title LIKE ? OR ingredients LIKE ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchTerm = "%" + keyword + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Recipe r = new Recipe(
                    rs.getInt("recipe_id"),
                    rs.getString("title"),
                    rs.getString("ingredients"),
                    rs.getString("instructions"),
                    rs.getString("category"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("created_at")
                );
                list.add(r);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error searching recipes: " + e.getMessage());
        }

        return list;
    }
}
