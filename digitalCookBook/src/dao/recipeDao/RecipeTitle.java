package dao.recipeDao;
import java.util.ArrayList;
import java.util.List;
import dao.dbConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class RecipeTitle {
    private int id;      // optional, useful for edit/delete
    private String title;

    public RecipeTitle(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }

    @Override
    public String toString() {
        return title;
    }
    
    public  static List<RecipeTitle> getRecipeTitles(int userId) throws ClassNotFoundException {
        List<RecipeTitle> list = new ArrayList<>();
        String sql = "SELECT recipe_id, title FROM recipe WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId); // set the current user ID
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new RecipeTitle(rs.getInt("recipe_id"), rs.getString("title")));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching recipe titles: " + e.getMessage());
        }

        return list;
    }
    
    public  static List<RecipeTitle> getAllRecipes() throws ClassNotFoundException {
        List<RecipeTitle> list = new ArrayList<>();
        String sql = "SELECT recipe_id, title FROM recipe  ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new RecipeTitle(rs.getInt("recipe_id"), rs.getString("title")));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching recipe titles: " + e.getMessage());
        }

        return list;
    }

    
}
