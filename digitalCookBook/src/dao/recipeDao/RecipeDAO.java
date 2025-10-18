package dao.recipeDao;
import dao.dbConnection.DBConnection;
/*
 * Author: @AmyAnup
 * Date: 13/10/25
 *
 * RecipeDAO.java
 * Simple DAO class to manage recipes in the database.
 * Supports: Add, Read (All), Update, Delete recipes.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Recipe model class
class Recipe {
    private int id;
    private String title;
    private String ingredients;
    private String instructions;
    private String category;
    private String createdBy;
    private Timestamp createdAt;

    // Constructor with ID (for existing recipes)
    public Recipe(int id, String title, String ingredients, String instructions, String category, String createdBy, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.category = category;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    // Constructor without ID (for new recipes)
    public Recipe(String title, String ingredients, String instructions, String category, String createdBy) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.category = category;
        this.createdBy = createdBy;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    public String getCategory() { return category; }
    public String getCreatedBy() { return createdBy; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public void setCategory(String category) { this.category = category; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

// DAO class to interact with the database
public class RecipeDAO {
    // Database connection info
//    private final String url = "jdbc:mysql://localhost:3306/recipemanagerdb"; // ✅ Corrected database name
//    private final String user = "root"; // XAMPP default
//    private final String password = ""; // XAMPP default

    // Add a new recipe
    public void addRecipe(Recipe recipe) throws ClassNotFoundException {
        String sql = "INSERT INTO recipe (title, ingredients, instructions, category, created_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
            stmt.setString(1, recipe.getTitle());
            stmt.setString(2, recipe.getIngredients());
            stmt.setString(3, recipe.getInstructions());
            stmt.setString(4, recipe.getCategory());
            stmt.setString(5, recipe.getCreatedBy());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                recipe.setId(rs.getInt(1));
            }
            System.out.println("✅ Recipe added successfully! ID: " + recipe.getId());

        } catch (SQLException e) {
            System.err.println("❌ Error adding recipe: " + e.getMessage());
        }
    }

    // Retrieve all recipes
    public List<Recipe> getAllRecipes(int UserId) throws ClassNotFoundException {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipe WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	((PreparedStatement) stmt).setInt(1, UserId);
            while (rs.next()) {
                Recipe r = new Recipe(
                	
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("ingredients"),
                        rs.getString("instructions"),
                        rs.getString("category"),
                        rs.getString("created_by"),
                        rs.getTimestamp("created_at")
                );
                list.add(r);
                
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching recipes: " + e.getMessage());
        }
        return list;
    }

    // Update an existing recipe
    public void updateRecipe(Recipe recipe) throws ClassNotFoundException {
        String sql = "UPDATE recipe SET title=?, ingredients=?, instructions=?, category=?, created_by=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipe.getTitle());
            stmt.setString(2, recipe.getIngredients());
            stmt.setString(3, recipe.getInstructions());
            stmt.setString(4, recipe.getCategory());
            stmt.setString(5, recipe.getCreatedBy());
            stmt.setInt(6, recipe.getId());

            int rows = stmt.executeUpdate();
            System.out.println("✅ " + rows + " recipe(s) updated.");

        } catch (SQLException e) {
            System.err.println("❌ Error updating recipe: " + e.getMessage());
        }
    }

    // Delete a recipe by ID
    public void deleteRecipe(int id) throws ClassNotFoundException {
        String sql = "DELETE FROM recipe WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println("🗑️ " + rows + " recipe(s) deleted.");

        } catch (SQLException e) {
            System.err.println("❌ Error deleting recipe: " + e.getMessage());
        }
    }

    // Test the DAO
//    public static void main(String[] args) {
//        RecipeDAO dao = new RecipeDAO();
//
//        // Add a recipe
//        Recipe newRecipe = new Recipe("Mashed Potatoes", "Potatoes, Butter, Milk, Salt", "Boil, mash, and mix all ingredients", "Side Dish", "Amy");
//        dao.addRecipe(newRecipe);
//
//        // List all recipes
//        List<Recipe> recipes = dao.getAllRecipes();
//        for (Recipe r : recipes) {
//            System.out.println(r.getId() + " | " + r.getTitle() + " | " + r.getCategory() + " | " + r.getCreatedAt());
//        }
//    }
}
