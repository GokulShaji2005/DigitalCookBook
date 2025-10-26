//
//package ui.Cook;
//
//import javax.swing.*;
//import dao.recipeDao.RecipeDAO;
//import model.Recipe;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class RecipeViewPanel extends JFrame {
//    private Recipe recipe;
//
//    public RecipeViewPanel(int recipeId) {
//        setTitle("Recipe Details");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setSize(1000, 650);
//        setLocationRelativeTo(null);
//        getContentPane().setBackground(new Color(245, 247, 250)); // light gray
//
//        // --- Use BorderLayout for the main frame ---
//        setLayout(new BorderLayout());
//
//        // Fetch recipe
//        try {
//            this.recipe = new RecipeDAO().getRecipeById(recipeId);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        if (this.recipe == null) {
//            add(new JLabel("Recipe not found!", SwingConstants.CENTER), BorderLayout.CENTER);
//            setVisible(true);
//            return;
//        }
//
//        // --- Card Panel ---
//        JPanel cardPanel = new JPanel(new BorderLayout());
//        cardPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
//                BorderFactory.createEmptyBorder(20, 20, 20, 20)
//        ));
//        cardPanel.setBackground(Color.WHITE);
//        cardPanel.setPreferredSize(new Dimension(850, 550));
//
//        // --- Top: Title ---
//        JLabel titleLabel = new JLabel(recipe.getTitle(), SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
//        titleLabel.setForeground(new Color(40, 40, 40));
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
//        cardPanel.add(titleLabel, BorderLayout.NORTH);
//
//        // --- Content Panel ---
//        JPanel contentPanel = new JPanel(new GridBagLayout());
//        contentPanel.setBackground(Color.WHITE);
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 20, 10, 20);
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.weighty = 1.0;
//
//        // --- Left: Category + Ingredients ---
//        JPanel leftPanel = new JPanel();
//        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
//        leftPanel.setBackground(Color.WHITE);
//
//        JLabel categoryLabel = new JLabel("Category: " + recipe.getCategory());
//        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JLabel ingredientsLabel = new JLabel("Ingredients:");
//        ingredientsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        ingredientsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
//
//        JTextArea ingredientsArea = new JTextArea();
//        String ingredientsStr = recipe.getIngredients();
//        if (ingredientsStr != null && !ingredientsStr.trim().isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            String[] ingredients = ingredientsStr.split("\\r?\\n");
//            for (String ing : ingredients) {
//                if (!ing.trim().isEmpty()) {
//                    sb.append("• ").append(ing.trim()).append("\n");
//                }
//            }
//            ingredientsArea.setText(sb.toString());
//        }
//
//        ingredientsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        ingredientsArea.setEditable(false);
//        ingredientsArea.setLineWrap(true);
//        ingredientsArea.setWrapStyleWord(true);
//        ingredientsArea.setOpaque(false);
//
//        JScrollPane ingScroll = new JScrollPane(ingredientsArea);
//        ingScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        ingScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        ingScroll.setPreferredSize(new Dimension(300, 200));
//        ingScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
//
//        leftPanel.add(categoryLabel);
//        leftPanel.add(Box.createVerticalStrut(10));
//        leftPanel.add(ingredientsLabel);
//        leftPanel.add(ingScroll);
//        leftPanel.add(Box.createVerticalGlue());
//
//        // --- Right: Steps ---
//        JPanel rightPanel = new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
//        rightPanel.setBackground(Color.WHITE);
//
//        JLabel stepsLabel = new JLabel("Steps:");
//        stepsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        stepsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        rightPanel.add(stepsLabel);
//        rightPanel.add(Box.createVerticalStrut(10));
//
//        JTextArea stepsArea = new JTextArea();
//        String stepsStr = recipe.getInstructions();
//        if (stepsStr != null && !stepsStr.trim().isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            String[] stepsArray = stepsStr.split("\\.");
//            int stepNumber = 1;
//            for (String step : stepsArray) {
//                step = step.trim();
//                if (!step.isEmpty()) {
//                    sb.append(stepNumber).append(". ").append(step).append(".\n\n");
//                    stepNumber++;
//                }
//            }
//            stepsArea.setText(sb.toString());
//        }
//        stepsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        stepsArea.setEditable(false);
//        stepsArea.setLineWrap(true);
//        stepsArea.setWrapStyleWord(true);
//        stepsArea.setOpaque(false);
//
//        JScrollPane stepsScroll = new JScrollPane(stepsArea);
//        stepsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        stepsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        stepsScroll.setPreferredSize(new Dimension(400, 250));
//        stepsScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
//
//        rightPanel.add(stepsScroll);
//
//        // --- Add left & right panels ---
//        gbc.gridx = 0;
//        gbc.weightx = 0.4;
//        contentPanel.add(leftPanel, gbc);
//
//        gbc.gridx = 1;
//        gbc.weightx = 0.6;
//        contentPanel.add(rightPanel, gbc);
//
//        cardPanel.add(contentPanel, BorderLayout.CENTER);
//
//        // --- Bottom: Back Button ---
//        JButton backButton = new JButton("← Back");
//        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        backButton.setBackground(new Color(230, 230, 230));
//        backButton.setFocusPainted(false);
//        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        bottomPanel.setBackground(Color.WHITE);
//        bottomPanel.add(backButton);
//        cardPanel.add(bottomPanel, BorderLayout.SOUTH);
//
//        // --- Wrapper panel for padding around the card ---
//        JPanel wrapperPanel = new JPanel(new BorderLayout());
//        wrapperPanel.setBackground(new Color(245, 247, 250));
//        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60)); // padding around the card
//        wrapperPanel.add(cardPanel, BorderLayout.CENTER);
//
//        add(wrapperPanel, BorderLayout.CENTER);
//
//        // --- Back Button Action (traditional) ---
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//            }
//        });
//
//        setVisible(true);
//    }
//}
//

package ui.Cook;

import javax.swing.*;
import javax.swing.border.Border;
import dao.recipeDao.RecipeDAO;
import model.Recipe;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeViewPanel extends JFrame {

    private Recipe recipe;

    // --- Define a modern color palette ---
    private static final Color COLOR_BACKGROUND = new Color(245, 247, 250);
    private static final Color COLOR_CARD = Color.WHITE;
    private static final Color COLOR_TEXT_PRIMARY = new Color(30, 30, 30);
    private static final Color COLOR_TEXT_SECONDARY = new Color(90, 90, 90);
    private static final Color COLOR_BORDER = new Color(220, 220, 220);
    private static final Color COLOR_ACCENT = new Color(52, 152, 219); // A nice blue

    // --- Define fonts ---
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    public RecipeViewPanel(int recipeId) {
        setTitle("Recipe Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700); // Slightly taller for better spacing
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);
        setLayout(new BorderLayout());

        // Fetch recipe
        try {
            this.recipe = new RecipeDAO().getRecipeById(recipeId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle exception gracefully
        }

        if (this.recipe == null) {
            JLabel notFoundLabel = new JLabel("Recipe not found!", SwingConstants.CENTER);
            notFoundLabel.setFont(FONT_HEADING);
            notFoundLabel.setForeground(COLOR_TEXT_SECONDARY);
            add(notFoundLabel, BorderLayout.CENTER);
            setVisible(true);
            return;
        }

        // --- Wrapper Panel (for overall padding) ---
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(COLOR_BACKGROUND);
        // Add more "air" around the central card
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // --- Card Panel (main content holder) ---
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(COLOR_CARD);
        // A subtle rounded border with good internal padding
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        wrapperPanel.add(cardPanel, BorderLayout.CENTER);

        // --- Top: Title ---
        JLabel titleLabel = new JLabel(recipe.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_TEXT_PRIMARY);
        // Add a subtle bottom border to separate title from content
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER),
                BorderFactory.createEmptyBorder(0, 0, 20, 0)
        ));
        cardPanel.add(titleLabel, BorderLayout.NORTH);

        // --- Center: Content Panel (Ingredients & Steps) ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(COLOR_CARD);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Padding from title
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // --- Left: Category + Ingredients ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_CARD);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15)); // Gutter space

        JLabel categoryLabel = new JLabel("Category: " + recipe.getCategory());
        categoryLabel.setFont(FONT_BODY.deriveFont(Font.BOLD));
        categoryLabel.setForeground(COLOR_TEXT_SECONDARY);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsLabel.setFont(FONT_HEADING);
        ingredientsLabel.setForeground(COLOR_TEXT_PRIMARY);
        ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientsLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 8, 0));

        JTextArea ingredientsArea = new JTextArea();
        ingredientsArea.setText(formatIngredients(recipe.getIngredients()));
        ingredientsArea.setFont(FONT_BODY);
        ingredientsArea.setForeground(COLOR_TEXT_SECONDARY);
        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        ingredientsArea.setOpaque(true); // Set Opaque to true for standard JTextArea
        ingredientsArea.setBackground(COLOR_CARD); // Match background

        JScrollPane ingScroll = createStyledScrollPane(ingredientsArea);

        leftPanel.add(categoryLabel);
        leftPanel.add(ingredientsLabel);
        leftPanel.add(ingScroll);

        // --- Right: Steps ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(COLOR_CARD);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); // Gutter space

        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setFont(FONT_HEADING);
        stepsLabel.setForeground(COLOR_TEXT_PRIMARY);
        stepsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stepsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JTextArea stepsArea = new JTextArea();
        stepsArea.setText(formatSteps(recipe.getInstructions()));
        stepsArea.setFont(FONT_BODY);
        stepsArea.setForeground(COLOR_TEXT_SECONDARY);
        stepsArea.setEditable(false);
        stepsArea.setLineWrap(true);
        stepsArea.setWrapStyleWord(true);
        stepsArea.setOpaque(true); // Set Opaque to true
        stepsArea.setBackground(COLOR_CARD); // Match background
        // Add padding inside the text area
        stepsArea.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane stepsScroll = createStyledScrollPane(stepsArea);

        rightPanel.add(stepsLabel);
        rightPanel.add(stepsScroll);

        // --- Add left & right panels to contentPanel ---
        gbc.gridx = 0;
        gbc.weightx = 0.4; // 40% width
        contentPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6; // 60% width
        contentPanel.add(rightPanel, gbc);

        cardPanel.add(contentPanel, BorderLayout.CENTER);

        // --- Bottom: Back Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align right
        bottomPanel.setBackground(COLOR_CARD);
        // Add a top border to separate from content
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDER));

        JButton backButton = new JButton("Back");
        backButton.setFont(FONT_BUTTON);
        backButton.setBackground(COLOR_ACCENT);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false); // Flat button
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Add internal padding to the button
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        bottomPanel.add(backButton);
        cardPanel.add(bottomPanel, BorderLayout.SOUTH);

        // --- Add the main card wrapper to the frame ---
        add(wrapperPanel, BorderLayout.CENTER);

        // --- Action ---
        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    /**
     * Helper method to format the ingredients string with bullet points.
     */
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

    /**
     * Helper method to format the steps string with numbering.
     */
    private String formatSteps(String stepsStr) {
        if (stepsStr == null || stepsStr.trim().isEmpty()) {
            return "No steps provided.";
        }
        StringBuilder sb = new StringBuilder();
        // Split by period, but also consider exclamation marks or question marks
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

    /**
     * Helper method to create a consistently styled JScrollPane.
     */
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