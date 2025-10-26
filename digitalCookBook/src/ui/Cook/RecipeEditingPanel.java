
package ui.Cook;

import dao.recipeDao.RecipeDAO;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeEditingPanel extends RecipeAddingPanel {
    private int recipeId;
    private Recipe existingRecipe;

    public RecipeEditingPanel(int recipeId, int userId) {
        super(userId); // inherit UI
        this.recipeId = recipeId;

        // Change header and button text
        JLabel headerLabel = (JLabel) ((JPanel) getComponent(0)).getComponent(0);
        headerLabel.setText("✏️ Edit Recipe");
        saveButton.setText("💾 Update Recipe");

        // Remove parent's save action
        for (var l : saveButton.getActionListeners()) saveButton.removeActionListener(l);

        // ✅ Add traditional ActionListener instead of lambda
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRecipe();
            }
        });

        loadRecipeDetails();
    }

    private void loadRecipeDetails() {
        try {
            RecipeDAO dao = new RecipeDAO();
            existingRecipe = dao.getRecipeById(recipeId);

            if (existingRecipe != null) {
                nameField.setText(existingRecipe.getTitle());
//                categoryField.setText(existingRecipe.getCategory());
                categoryCombo.setSelectedItem(existingRecipe.getCategory());

                ingredientsArea.setText(existingRecipe.getIngredients());
                stepsArea.setText(existingRecipe.getInstructions());
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Recipe not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading recipe: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
//
//    private void updateRecipe() {
//        String name = nameField.getText().trim();
////        String category = categoryField.getText().trim();
////        String category = categoryField.getText().trim();
//        String category = (String) categoryCombo.getSelectedItem();
//
//        String ingredients = ingredientsArea.getText().trim();
//        String steps = stepsArea.getText().trim();
//
//        if (name.isEmpty() || category.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "⚠ Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        existingRecipe.setTitle(name);
//        existingRecipe.setCategory(category);
//        existingRecipe.setIngredients(ingredients);
//        existingRecipe.setInstructions(steps);
//        existingRecipe.setUser_id(userId);
//
//        try {
//            RecipeDAO dao = new RecipeDAO();
//            dao.updateRecipe(existingRecipe);
//            JOptionPane.showMessageDialog(this, "✅ Recipe updated successfully!");
//            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
//            if (frame != null) frame.dispose();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "❌ Update failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            ex.printStackTrace();
//        }
//    }
    
    private void updateRecipe() {
        String name = nameField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        String ingredients = ingredientsArea.getText().trim();
        String steps = stepsArea.getText().trim();

        if (name.isEmpty() || category.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        existingRecipe.setTitle(name);
        existingRecipe.setCategory(category);
        existingRecipe.setIngredients(ingredients);
        existingRecipe.setInstructions(steps);
        existingRecipe.setUser_id(userId);

        if (selectedImageFile != null) {
            existingRecipe.setImagePath(selectedImageFile.getAbsolutePath());
        }

        try {
            RecipeDAO dao = new RecipeDAO();
            dao.updateRecipe(existingRecipe);
            JOptionPane.showMessageDialog(this, "✅ Recipe updated successfully!");

            if (recipeAddedListener != null) recipeAddedListener.run();

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Update failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}

