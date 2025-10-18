package db;

/*
 * Author: @AmyAnup
 * Date: 14/10/25
 *
 * CategoryDAO.java
 * DAO class to manage recipe categories in the database.
 * Supports: Add, Read (All), Update, Delete categories.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Category model class
class Category {
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

public class CategoryDAO {
    private Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
    }

    // Add a new category
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO categories(name) VALUES(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding category: " + e.getMessage());
            return false;
        }
    }

    // Get all categories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
        }
        return categories;
    }

    // Get category by ID
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching category: " + e.getMessage());
        }
        return null;
    }

    // Update a category
    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET name=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating category: " + e.getMessage());
            return false;
        }
    }

    // Delete a category
    public boolean deleteCategory(int id) {
        String sql = "DELETE FROM categories WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting category: " + e.getMessage());
            return false;
        }
    }

    // Check if category exists by name (to avoid duplicates)
    public boolean categoryExists(String name) {
        String sql = "SELECT id FROM categories WHERE name=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if exists
        } catch (SQLException e) {
            System.out.println("Error checking category: " + e.getMessage());
            return false;
        }
    }
}

