package ui.Cook;

import javax.swing.*;
import dao.recipeDao.RecipeDAO;
import model.Recipe;
import java.awt.*;

public class RecipeViewPanel extends JFrame {
    private Recipe recipe;

    public RecipeViewPanel(int recipeId) {
        setTitle("Recipe Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only this frame
        setSize(1000, 650);
        setLocationRelativeTo(null); // center on screen
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 247, 250)); // light gray

        // Fetch recipe
        try {
            this.recipe = new RecipeDAO().getRecipeById(recipeId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (this.recipe == null) {
            add(new JLabel("Recipe not found!"));
            setVisible(true);
            return;
        }

        // --- Card Panel ---
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        cardPanel.setBackground(Color.WHITE);

        // --- Top: Title ---
        JLabel titleLabel = new JLabel(recipe.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        cardPanel.add(titleLabel, BorderLayout.NORTH);

        // --- Content Panel ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // --- Left: Category + Ingredients ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        JLabel categoryLabel = new JLabel("Category: " + recipe.getCategory());
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        JTextArea ingredientsArea = new JTextArea();
        String ingredientsStr = recipe.getIngredients();
        if (ingredientsStr != null && !ingredientsStr.trim().isEmpty()) {
            StringBuilder sb = new StringBuilder();

            // Split input into lines (each ingredient is on a new line)
            String[] ingredients = ingredientsStr.split("\\r?\\n");

            // Go through each line
            for (String ing : ingredients) {
                if (!ing.trim().isEmpty()) {  // skip empty lines
                    sb.append("• ").append(ing.trim()).append("\n");
                }
            }

            // Show formatted text in your ingredientsArea
            ingredientsArea.setText(sb.toString());
        }

        ingredientsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        ingredientsArea.setOpaque(false);

        JScrollPane ingScroll = new JScrollPane(ingredientsArea);
        ingScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ingScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ingScroll.setPreferredSize(new Dimension(300, 150));
        ingScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        leftPanel.add(categoryLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(ingredientsLabel);
        leftPanel.add(ingScroll);
        leftPanel.add(Box.createVerticalGlue());

        // --- Right: Steps ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        stepsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(stepsLabel);
        rightPanel.add(Box.createVerticalStrut(10));

        JTextArea stepsArea = new JTextArea();
        String stepsStr = recipe.getInstructions();
        if (stepsStr != null && !stepsStr.trim().isEmpty()) {
            StringBuilder sb = new StringBuilder();

            // Split the string based on a full stop (.)
            String[] stepsArray = stepsStr.split("\\.");

            int stepNumber = 1;
            for (String step : stepsArray) {
                step = step.trim(); // Remove extra spaces
                if (!step.isEmpty()) { // Skip blank entries
                    sb.append(stepNumber).append(". ").append(step).append(".\n\n");
                    stepNumber++;
                }
            }

            stepsArea.setText(sb.toString());
        }
        stepsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stepsArea.setEditable(false);
        stepsArea.setLineWrap(true);
        stepsArea.setWrapStyleWord(true);
        stepsArea.setOpaque(false);

        JScrollPane stepsScroll = new JScrollPane(stepsArea);
        stepsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        stepsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        stepsScroll.setPreferredSize(new Dimension(350, 250));
        stepsScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        rightPanel.add(stepsScroll);

        // --- Add left & right panels ---
        gbc.gridx = 0;
        gbc.weightx = 0.4;
        contentPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        contentPanel.add(rightPanel, gbc);

        cardPanel.add(contentPanel, BorderLayout.CENTER);

        // --- Bottom: Back Button ---
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(new Color(230, 230, 230));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backButton);

        cardPanel.add(bottomPanel, BorderLayout.SOUTH);

        // --- Add cardPanel to frame ---
        add(cardPanel);

        // --- Back Button Action ---
        backButton.addActionListener(e -> dispose()); // simply closes this frame

        setVisible(true);
    }
}
