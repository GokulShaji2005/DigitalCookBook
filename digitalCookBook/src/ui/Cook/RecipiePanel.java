package ui.Cook;

import javax.swing.*;
import java.awt.*;

public class RecipiePanel extends JPanel {

    private JPanel recipeListPanel;
    private JButton addRecipeButton;

    public RecipiePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 Header
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Your Recipes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        addRecipeButton = new JButton("➕ Add Recipe");
        stylePrimaryButton(addRecipeButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(addRecipeButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 🔹 Recipe List
        recipeListPanel = new JPanel();
        recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));
        recipeListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Example entries (optional)
        addRecipe("Spaghetti Bolognese");
        addRecipe("Chocolate Cake");
        addRecipe("Chicken Curry");
    }

    public void addRecipe(String recipeName) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel nameLabel = new JLabel(recipeName);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        btnPanel.setBackground(new Color(250, 250, 250));

        JButton viewBtn = new JButton("View");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        for (JButton btn : new JButton[]{viewBtn, editBtn, deleteBtn}) {
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            btn.setBackground(new Color(230, 230, 230));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnPanel.add(viewBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        card.add(nameLabel, BorderLayout.WEST);
        card.add(btnPanel, BorderLayout.EAST);

        recipeListPanel.add(Box.createVerticalStrut(8));
        recipeListPanel.add(card);
        recipeListPanel.revalidate();
        recipeListPanel.repaint();
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(60, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public JButton getAddRecipeButton() {
        return addRecipeButton;
    }

    public void clearRecipes() {
        recipeListPanel.removeAll();
        recipeListPanel.revalidate();
        recipeListPanel.repaint();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Recipe Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);

            RecipiePanel panel = new RecipiePanel();
            frame.setContentPane(panel);

            frame.setVisible(true);
        });
    }

}
