//
//package ui.Cook;
//
//import dao.recipeDao.RecipeDAO;
//import model.Recipe;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class RecipeAddingPanel extends JPanel {
//
//    public Runnable recipeAddedListener;
//    protected JTextField nameField;
////    protected JTextField categoryField;
//    protected JComboBox<String> categoryCombo;
//
//    protected JTextArea ingredientsArea;
//    protected JTextArea stepsArea;
//    protected JButton saveButton;
//    protected int userId;
//
//    public void setRecipeAddedListener(Runnable listener) {
//        this.recipeAddedListener = listener;
//    }
//
//    public RecipeAddingPanel(int userId) {
//        this.userId = userId;
//        setLayout(new BorderLayout());
//        setBackground(new Color(245, 247, 250));
//
//        // Card Panel
//        JPanel cardPanel = new JPanel(new BorderLayout());
//        cardPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
//                BorderFactory.createEmptyBorder(20, 20, 20, 20)
//        ));
//        cardPanel.setBackground(Color.WHITE);
//
//        // Header
//        JLabel titleLabel = new JLabel("➕ Add New Recipe");
//        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
//        titleLabel.setForeground(new Color(40, 40, 40));
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
//        cardPanel.add(titleLabel, BorderLayout.NORTH);
//
//        // Split Layout
//        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 25, 0));
//        contentPanel.setBackground(Color.WHITE);
//
//        // === LEFT PANEL ===
//        JPanel leftPanel = new JPanel();
//        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
//        leftPanel.setBackground(Color.WHITE);
//
//        JLabel nameLabel = createLabel("Recipe Name:");
//        nameField = createTextField();
//
////        JLabel categoryLabel = createLabel("Category:");
////        categoryField = createTextField();
//        
//        JLabel categoryLabel = createLabel("Category:");
//
//     // Example static category list
//     String[] categories = {"Veg","Non-Veg","Dessert"};
//     categoryCombo = new JComboBox<>(categories);
//     styleComboBox(categoryCombo);
//
//
//        JLabel ingredientsLabel = createLabel("Ingredients (one per line):");
//        ingredientsArea = new JTextArea(8, 20);
//        styleTextArea(ingredientsArea);
//        JScrollPane ingredientScroll = new JScrollPane(ingredientsArea);
//        ingredientScroll.setBorder(BorderFactory.createEmptyBorder());
//     
//
//        leftPanel.add(nameLabel);
//        leftPanel.add(Box.createVerticalStrut(5));
//        leftPanel.add(nameField);
//        leftPanel.add(Box.createVerticalStrut(10));
//        leftPanel.add(categoryLabel);
//        leftPanel.add(Box.createVerticalStrut(5));
////        leftPanel.add(categoryField);
//        leftPanel.add(categoryCombo);
//
//        leftPanel.add(Box.createVerticalStrut(10));
//        leftPanel.add(ingredientsLabel);
//        leftPanel.add(Box.createVerticalStrut(5));
//        leftPanel.add(ingredientScroll);
//        leftPanel.add(Box.createVerticalStrut(15));
//
//        // === RIGHT PANEL ===
//        JPanel rightPanel = new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
//        rightPanel.setBackground(Color.WHITE);
//
//        JLabel stepsLabel = createLabel("Steps:");
//        stepsArea = new JTextArea(15, 30);
//        styleTextArea(stepsArea);
//        JScrollPane stepsScroll = new JScrollPane(stepsArea);
//        stepsScroll.setBorder(BorderFactory.createEmptyBorder());
//
//        rightPanel.add(stepsLabel);
//        rightPanel.add(Box.createVerticalStrut(5));
//        rightPanel.add(stepsScroll);
//
//        contentPanel.add(leftPanel);
//        contentPanel.add(rightPanel);
//
//        cardPanel.add(contentPanel, BorderLayout.CENTER);
//
//        // === BOTTOM BUTTONS ===
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
//        bottomPanel.setBackground(Color.WHITE);
//
//        saveButton = new JButton("💾 Save Recipe");
//        JButton cancelButton = new JButton("Cancel");
//        stylePrimaryButton(saveButton);
//        styleSecondaryButton(cancelButton);
//
//        bottomPanel.add(cancelButton);
//        bottomPanel.add(saveButton);
//        cardPanel.add(bottomPanel, BorderLayout.SOUTH);
//
//        add(cardPanel, BorderLayout.CENTER);
//
//        // ✅ Add traditional ActionListener for SAVE button
//        saveButton.addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                saveRecipe();
//                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(RecipeAddingPanel.this);
//                if (frame != null) frame.dispose();
//            }
//        });
//
//        // Cancel button closes window
//        cancelButton.addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(RecipeAddingPanel.this);
//                if (frame != null) frame.dispose();
//            }
//        });
//    }
//
//    private void saveRecipe() {
//        String name = nameField.getText().trim();
////        String category = categoryField.getText().trim();
//        String category = (String) categoryCombo.getSelectedItem();
//
//        String ingredients = ingredientsArea.getText().trim();
//        String steps = stepsArea.getText().trim();
//
//        if (name.isEmpty() || category.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "⚠ Please fill all fields before saving!", "Missing Info", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        Recipe recipe = new Recipe();
//        recipe.setTitle(name);
//        recipe.setCategory(category);
//        recipe.setIngredients(ingredients);
//        recipe.setInstructions(steps);
//        recipe.setUser_id(userId);
//
//        try {
//            RecipeDAO dao = new RecipeDAO();
//            dao.addRecipe(recipe);
//            JOptionPane.showMessageDialog(this, "✅ Recipe added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//            clearFields();
//            if (recipeAddedListener != null) recipeAddedListener.run();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "❌ Error saving recipe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            ex.printStackTrace();
//        }
//    }
//
//    private void clearFields() {
//        nameField.setText("");
////        categoryField.setText("");
//        ingredientsArea.setText("");
//        stepsArea.setText("");
//    }
//
//    private JLabel createLabel(String text) {
//        JLabel label = new JLabel(text);
//        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
//        label.setAlignmentX(Component.LEFT_ALIGNMENT);
//        return label;
//    }
//
//    private JTextField createTextField() {
//        JTextField field = new JTextField();
//        styleTextField(field);
//        return field;
//    }
//
//    private void styleTextField(JTextField field) {
//        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        field.setForeground(Color.DARK_GRAY);
//        field.setBackground(Color.WHITE);
//        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
//        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
//    }
//
//    private void styleTextArea(JTextArea area) {
//        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        area.setForeground(Color.DARK_GRAY);
//        area.setBackground(Color.WHITE);
//        area.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
//        area.setLineWrap(true);
//        area.setWrapStyleWord(true);
//    }
//
//    private void stylePrimaryButton(JButton button) {
//        button.setBackground(new Color(60, 120, 200));
//        button.setForeground(Color.WHITE);
//        button.setFocusPainted(false);
//        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
//        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
//    }
//
//    private void styleSecondaryButton(JButton button) {
//        button.setBackground(new Color(220, 220, 220));
//        button.setForeground(Color.DARK_GRAY);
//        button.setFocusPainted(false);
//        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
//        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
//    }
//    
//    private void styleComboBox(JComboBox<String> comboBox) {
//        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        comboBox.setForeground(Color.DARK_GRAY);
//        comboBox.setBackground(Color.WHITE);
//        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
//        comboBox.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
//        comboBox.setEditable(false); // make true if you want user to type custom category
//    }
//
//}
package ui.Cook;

import dao.recipeDao.RecipeDAO;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecipeAddingPanel extends JPanel {

    public Runnable recipeAddedListener;
    protected JTextField nameField;
    protected JComboBox<String> categoryCombo;
    protected JTextArea ingredientsArea;
    protected JTextArea stepsArea;
    protected JButton saveButton;
    protected int userId;

    // New: Image upload
    private JLabel imagePreview;
    private JButton uploadImageButton;
    protected File selectedImageFile;

    public void setRecipeAddedListener(Runnable listener) {
        this.recipeAddedListener = listener;
    }

    public RecipeAddingPanel(int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // Card Panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        cardPanel.setBackground(Color.WHITE);

        // Header
        JLabel titleLabel = new JLabel("➕ Add New Recipe");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        cardPanel.add(titleLabel, BorderLayout.NORTH);

        // Split Layout
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        contentPanel.setBackground(Color.WHITE);

        // === LEFT PANEL ===
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        JLabel nameLabel = createLabel("Recipe Name:");
        nameField = createTextField();

        JLabel categoryLabel = createLabel("Category:");
        String[] categories = {"Veg", "Non-Veg", "Dessert"};
        categoryCombo = new JComboBox<>(categories);
        styleComboBox(categoryCombo);

        JLabel ingredientsLabel = createLabel("Ingredients (one per line):");
        ingredientsArea = new JTextArea(8, 20);
        styleTextArea(ingredientsArea);
        JScrollPane ingredientScroll = new JScrollPane(ingredientsArea);
        ingredientScroll.setBorder(BorderFactory.createEmptyBorder());

        // --- Image Upload ---
        JLabel imageLabel = createLabel("Upload Recipe Image:");
        imagePreview = new JLabel();
        imagePreview.setPreferredSize(new Dimension(250, 150));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        uploadImageButton = new JButton("Choose Image");
        stylePrimaryButton(uploadImageButton);

        uploadImageButton.addActionListener(e -> chooseImage());

        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(nameField);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(categoryLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(categoryCombo);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(ingredientsLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(ingredientScroll);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(imageLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(imagePreview);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(uploadImageButton);

        // === RIGHT PANEL ===
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JLabel stepsLabel = createLabel("Steps:");
        stepsArea = new JTextArea(15, 30);
        styleTextArea(stepsArea);
        JScrollPane stepsScroll = new JScrollPane(stepsArea);
        stepsScroll.setBorder(BorderFactory.createEmptyBorder());

        rightPanel.add(stepsLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(stepsScroll);

        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);

        cardPanel.add(contentPanel, BorderLayout.CENTER);

        // === BOTTOM BUTTONS ===
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setBackground(Color.WHITE);

        saveButton = new JButton("💾 Save Recipe");
        JButton cancelButton = new JButton("Cancel");
        stylePrimaryButton(saveButton);
        styleSecondaryButton(cancelButton);

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);
        cardPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(cardPanel, BorderLayout.CENTER);

        // Save action
        saveButton.addActionListener(e -> saveRecipe());
        cancelButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) frame.dispose();
        });
    }

    // --- Image chooser ---
    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(selectedImageFile.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH);
            imagePreview.setIcon(new ImageIcon(scaled));
        }
    }

//    private void saveRecipe() {
//        String name = nameField.getText().trim();
//        String category = (String) categoryCombo.getSelectedItem();
//        String ingredients = ingredientsArea.getText().trim();
//        String steps = stepsArea.getText().trim();
//
//        if (name.isEmpty() || category.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "⚠ Please fill all fields before saving!", "Missing Info", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        Recipe recipe = new Recipe();
//        recipe.setTitle(name);
//        recipe.setCategory(category);
//        recipe.setIngredients(ingredients);
//        recipe.setInstructions(steps);
//        recipe.setUser_id(userId);
//
//        if (selectedImageFile != null) {
//            recipe.setImagePath(selectedImageFile.getAbsolutePath());
//        }
//
//        try {
//            RecipeDAO dao = new RecipeDAO();
//            dao.addRecipe(recipe);
//            JOptionPane.showMessageDialog(this, "✅ Recipe added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//            clearFields();
//            if (recipeAddedListener != null) recipeAddedListener.run();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "❌ Error saving recipe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            ex.printStackTrace();
//        }
//    }
    private void saveRecipe() {
        String name = nameField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        String ingredients = ingredientsArea.getText().trim();
        String steps = stepsArea.getText().trim();

        if (name.isEmpty() || category.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Please fill all fields before saving!", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(name);
        recipe.setCategory(category);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(steps);
        recipe.setUser_id(userId);

        if (selectedImageFile != null) {
            recipe.setImagePath(selectedImageFile.getAbsolutePath());
        }

        try {
            RecipeDAO dao = new RecipeDAO();
            dao.addRecipe(recipe);
            JOptionPane.showMessageDialog(this, "✅ Recipe added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Notify listener (if any)
            if (recipeAddedListener != null) recipeAddedListener.run();

            // Close the window
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) frame.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error saving recipe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.setText("");
        ingredientsArea.setText("");
        stepsArea.setText("");
        imagePreview.setIcon(null);
        selectedImageFile = null;
        categoryCombo.setSelectedIndex(0);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        styleTextField(field);
        return field;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.DARK_GRAY);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
    }

    private void styleTextArea(JTextArea area) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(Color.DARK_GRAY);
        area.setBackground(Color.WHITE);
        area.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(60, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(new Color(220, 220, 220));
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setForeground(Color.DARK_GRAY);
        comboBox.setBackground(Color.WHITE);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        comboBox.setEditable(false);
    }
}
