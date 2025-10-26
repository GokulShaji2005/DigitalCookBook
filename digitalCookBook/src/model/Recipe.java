

package model;

import java.sql.Timestamp;

public class Recipe {
    private int recipe_id;
    private String title;
    private String ingredients;
    private String instructions;
    private String category;
    private int user_id;
    private Timestamp createdAt;
    private String imagePath; // ✅ new field for image path

    // --- Constructors ---
    public Recipe(int recipe_id, String title, String ingredients, String instructions,
                  String category, int user_id, Timestamp createdAt, String imagePath) {
        this.recipe_id = recipe_id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.category = category;
        this.user_id = user_id;
        this.createdAt = createdAt;
        this.imagePath = imagePath;
    }

    // Existing constructor (for backward compatibility)
    public Recipe(int recipe_id, String title, String ingredients, String instructions,
                  String category, int user_id, Timestamp createdAt) {
        this(recipe_id, title, ingredients, instructions, category, user_id, createdAt, null);
    }

    // Constructor for adding a new recipe (image optional)
    public Recipe(String title, String ingredients, String instructions, String category,
                  int user_id, String imagePath) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.category = category;
        this.user_id = user_id;
        this.imagePath = imagePath;
    }

    // Old constructor (still works if you don’t use image)
    public Recipe(String title, String ingredients, String instructions, String category, int user_id) {
        this(title, ingredients, instructions, category, user_id, null);
    }

    // Default constructor
    public Recipe() {}

    // --- Getters & Setters ---
    public int getRecipe_Id() { return recipe_id; }
    public void setRecipe_Id(int recipe_id) { this.recipe_id = recipe_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getImagePath() { return imagePath; }          // ✅ Added
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }  // ✅ Added
}
