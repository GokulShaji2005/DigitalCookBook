package ui.Cook;

import javax.swing.*;
import javax.swing.border.Border;
import dao.recipeDao.RecipeDAO;
import model.Recipe;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import java.awt.*;
import model.Recipe;
import dao.recipeDao.RecipeDAO;

public class RecipeViewPanel extends JFrame {

    private Recipe recipe;

    // --- Modern color palette ---
    private static final Color COLOR_BACKGROUND = new Color(245, 247, 250);
    private static final Color COLOR_CARD = Color.WHITE;
    private static final Color COLOR_TEXT_PRIMARY = new Color(30, 30, 30);
    private static final Color COLOR_TEXT_SECONDARY = new Color(90, 90, 90);
    private static final Color COLOR_BORDER = new Color(220, 220, 220);
    private static final Color COLOR_ACCENT = new Color(52, 152, 219);

    // --- Fonts ---
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    public RecipeViewPanel(int recipeId) {
        setTitle("Recipe Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);
        setLayout(new BorderLayout());

        // Fetch recipe
        try {
            this.recipe = new RecipeDAO().getRecipeById(recipeId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (this.recipe == null) {
            JLabel notFoundLabel = new JLabel("Recipe not found!", SwingConstants.CENTER);
            notFoundLabel.setFont(FONT_HEADING);
            notFoundLabel.setForeground(COLOR_TEXT_SECONDARY);
            add(notFoundLabel, BorderLayout.CENTER);
            setVisible(true);
            return;
        }

        // --- Wrapper Panel ---
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(COLOR_BACKGROUND);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // --- Card Panel ---
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(COLOR_CARD);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        wrapperPanel.add(cardPanel, BorderLayout.CENTER);

        // --- Top Panel: Image + Title ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(COLOR_CARD);

        // Recipe Image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon;

        if (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) {
            icon = new ImageIcon(recipe.getImagePath());
        } else {
            icon = new ImageIcon("src/images/default_recipe.png"); // replace with default image path
        }

        Image scaled = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
        topPanel.add(imageLabel, BorderLayout.CENTER);

        // Recipe Title
        JLabel titleLabel = new JLabel(recipe.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        topPanel.add(titleLabel, BorderLayout.SOUTH);

        cardPanel.add(topPanel, BorderLayout.NORTH);

        // --- Center: Ingredients & Steps ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(COLOR_CARD);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Left: Category + Ingredients
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_CARD);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        JLabel categoryLabel = new JLabel("Category: " + recipe.getCategory());
        categoryLabel.setFont(FONT_BODY.deriveFont(Font.BOLD));
        categoryLabel.setForeground(COLOR_TEXT_SECONDARY);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsLabel.setFont(FONT_HEADING);
        ingredientsLabel.setForeground(COLOR_TEXT_PRIMARY);
        ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientsLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 8, 0));

        JTextArea ingredientsArea = new JTextArea(formatIngredients(recipe.getIngredients()));
        ingredientsArea.setFont(FONT_BODY);
        ingredientsArea.setForeground(COLOR_TEXT_SECONDARY);
        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        ingredientsArea.setOpaque(true);
        ingredientsArea.setBackground(COLOR_CARD);

        JScrollPane ingScroll = createStyledScrollPane(ingredientsArea);
        leftPanel.add(categoryLabel);
        leftPanel.add(ingredientsLabel);
        leftPanel.add(ingScroll);

        // Right: Steps
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(COLOR_CARD);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setFont(FONT_HEADING);
        stepsLabel.setForeground(COLOR_TEXT_PRIMARY);
        stepsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stepsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JTextArea stepsArea = new JTextArea(formatSteps(recipe.getInstructions()));
        stepsArea.setFont(FONT_BODY);
        stepsArea.setForeground(COLOR_TEXT_SECONDARY);
        stepsArea.setEditable(false);
        stepsArea.setLineWrap(true);
        stepsArea.setWrapStyleWord(true);
        stepsArea.setOpaque(true);
        stepsArea.setBackground(COLOR_CARD);
        stepsArea.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane stepsScroll = createStyledScrollPane(stepsArea);
        rightPanel.add(stepsLabel);
        rightPanel.add(stepsScroll);

        // Add left & right panels
        gbc.gridx = 0;
        gbc.weightx = 0.4;
        contentPanel.add(leftPanel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        contentPanel.add(rightPanel, gbc);

        cardPanel.add(contentPanel, BorderLayout.CENTER);

        // --- Bottom: Back Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(COLOR_CARD);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDER));

        JButton backButton = new JButton("Back");
        backButton.setFont(FONT_BUTTON);
        backButton.setBackground(COLOR_ACCENT);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        bottomPanel.add(backButton);
        cardPanel.add(bottomPanel, BorderLayout.SOUTH);

        // --- Add main wrapper ---
        add(wrapperPanel, BorderLayout.CENTER);

        // Back button action
        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    // --- Helper: Ingredients formatting ---
    private String formatIngredients(String ingredientsStr) {
        if (ingredientsStr == null || ingredientsStr.trim().isEmpty()) {
            return "No ingredients listed.";
        }
        StringBuilder sb = new StringBuilder();
        String[] ingredients = ingredientsStr.split("\\r?\\n");
        for (String ing : ingredients) {
            if (!ing.trim().isEmpty()) {
                sb.append("• ").append(ing.trim()).append("\n");
            }
        }
        return sb.toString();
    }

    // --- Helper: Steps formatting ---
    private String formatSteps(String stepsStr) {
        if (stepsStr == null || stepsStr.trim().isEmpty()) {
            return "No steps provided.";
        }
        StringBuilder sb = new StringBuilder();
        String[] stepsArray = stepsStr.split("(?<=[.!?])\\s*");
        int stepNumber = 1;
        for (String step : stepsArray) {
            step = step.trim();
            if (!step.isEmpty()) {
                sb.append(stepNumber).append(". ").append(step).append("\n\n");
                stepNumber++;
            }
        }
        return sb.toString();
    }

    // --- Styled JScrollPane ---
    private JScrollPane createStyledScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scrollPane.getViewport().setBackground(COLOR_CARD);
        scrollPane.setBackground(COLOR_CARD);
        return scrollPane;
    }
}
