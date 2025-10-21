package model;

import java.sql.Timestamp;

//✅ Make Recipe class public if you access it outside this file (optional)
public class Recipe {
 private int recipe_id;
 private String title;
 private String ingredients;
 private String instructions;
 private String category;
 private int user_id;
 private Timestamp createdAt;

 // Constructor with ID (for existing recipes)
 public Recipe(int recipe_id, String title, String ingredients, String instructions,
               String category, int user_id, Timestamp createdAt) {
     this.recipe_id = recipe_id;
     this.title = title;
     this.ingredients = ingredients;
     this.instructions = instructions;
     this.category = category;
     this.user_id = user_id;
     this.createdAt = createdAt;
 }

 // Constructor without ID (for new recipes)
 public Recipe(String title, String ingredients, String instructions, String category, int user_id) {
     this.title = title;
     this.ingredients = ingredients;
     this.instructions = instructions;
     this.category = category;
     this.user_id = user_id;
 }
 public Recipe() {
	    // Default constructor for cases where you set fields later
	}


 // Getters and setters
 public int getRecipe_Id() { return recipe_id; }
 public String getTitle() { return title; }
 public String getIngredients() { return ingredients; }
 public String getInstructions() { return instructions; }
 public String getCategory() { return category; }
 public int getUser_id() { return user_id; }
 public Timestamp getCreatedAt() { return createdAt; }

 public void setRecipe_Id(int recipe_id) { this.recipe_id = recipe_id; }
 public void setTitle(String title) { this.title = title; }
 public void setIngredients(String ingredients) { this.ingredients = ingredients; }
 public void setInstructions(String instructions) { this.instructions = instructions; }
 public void setCategory(String category) { this.category = category; }
 public void setUser_id(int user_id) { this.user_id = user_id; }
 public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
