//package ui.Cook;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.List;
//
//public class RecipeViewPanel extends JPanel {
//
//    public RecipeViewPanel(String recipeName, String category, List<String> ingredients, List<String> steps) {
//        // Use GridBagLayout on the main panel to center the cardPanel
//        setLayout(new GridBagLayout());
//        setBackground(new Color(245, 247, 250)); // light background for the panel
//
//        // Card panel container
//        // Removed setPreferredSize(new Dimension(800, 500)) so it can resize freely
//        JPanel cardPanel = new JPanel(new BorderLayout());
//        cardPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
//                BorderFactory.createEmptyBorder(20, 20, 20, 20)
//        ));
//        cardPanel.setBackground(Color.WHITE);
//
//        // --- Header ---
//        JLabel titleLabel = new JLabel(recipeName);
//        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
//        titleLabel.setForeground(new Color(40, 40, 40));
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // Increased bottom padding
//
//        cardPanel.add(titleLabel, BorderLayout.NORTH);
//
//        // --- Main content ---
//        JPanel contentPanel = new JPanel();
//        // Use GridBagLayout for flexible, weighted columns
//        contentPanel.setLayout(new GridBagLayout());
//        contentPanel.setBackground(Color.WHITE);
//        GridBagConstraints contentGbc = new GridBagConstraints();
//        contentGbc.insets = new Insets(0, 5, 0, 5);
//        contentGbc.fill = GridBagConstraints.BOTH;
//
//        // --- Left Panel (Recipe Info) ---
//        JPanel leftPanel = new JPanel();
//        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
//        leftPanel.setBackground(Color.WHITE);
//
//        JLabel categoryLabel = new JLabel("Category: " + category);
//        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
//        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JLabel ingredientsLabel = new JLabel("Ingredients:");
//        ingredientsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        ingredientsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Spacing
//
//        JPanel ingredientListPanel = new JPanel();
//        ingredientListPanel.setLayout(new BoxLayout(ingredientListPanel, BoxLayout.Y_AXIS));
//        ingredientListPanel.setBackground(Color.WHITE);
//        ingredientListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        for (String ing : ingredients) {
//            JLabel ingLabel = new JLabel("• " + ing);
//            ingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//            ingredientListPanel.add(ingLabel);
//            ingredientListPanel.add(Box.createVerticalStrut(4));
//        }
//
//        // Image placeholder as square
//        JPanel imagePanel = new JPanel();
//        imagePanel.setPreferredSize(new Dimension(150, 150));
//        imagePanel.setMaximumSize(new Dimension(150, 150));
//        imagePanel.setBackground(new Color(200, 200, 200));
//        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
//        imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        leftPanel.add(categoryLabel);
//        leftPanel.add(Box.createVerticalStrut(10));
//        leftPanel.add(ingredientsLabel);
//        leftPanel.add(Box.createVerticalStrut(5));
//        leftPanel.add(ingredientListPanel);
//        leftPanel.add(Box.createVerticalStrut(20));
//        leftPanel.add(imagePanel);
//        // ADD FILLER: This ensures the components stay at the top of the left column
//        leftPanel.add(Box.createVerticalGlue());
//
//
//        // --- Right Panel (Steps) ---
//        JPanel rightPanel = new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
//        rightPanel.setBackground(Color.WHITE);
//        rightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//
//        JLabel stepsLabel = new JLabel("Steps:");
//        stepsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        stepsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        // Container for steps text (inside scroll pane)
//        JPanel stepsContentPanel = new JPanel();
//        stepsContentPanel.setLayout(new BoxLayout(stepsContentPanel, BoxLayout.Y_AXIS));
//        stepsContentPanel.setBackground(Color.WHITE);
//        stepsContentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        for (int i = 0; i < steps.size(); i++) {
//            // Using HTML for basic line wrapping if steps are long
//            JLabel stepLabel = new JLabel("<html><body style='width: 350px;'><b>" + (i + 1) + ".</b> " + steps.get(i) + "</body></html>");
//            stepLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//            stepsContentPanel.add(stepLabel);
//            stepsContentPanel.add(Box.createVerticalStrut(10));
//        }
//        
//        // Add a vertical glue at the bottom of the stepsContentPanel
//        // so the steps stay aligned at the top when scrolling is not needed
//        stepsContentPanel.add(Box.createVerticalGlue());
//
//        JScrollPane stepScroll = new JScrollPane(stepsContentPanel);
//        stepScroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
//        stepScroll.getVerticalScrollBar().setUnitIncrement(16);
//        stepScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll
//
//        rightPanel.add(stepsLabel);
//        rightPanel.add(Box.createVerticalStrut(10));
//        // Add the scroll pane, and give it the full height/width
//        rightPanel.add(stepScroll); 
//
//
//        // Add Left Panel to Content Panel
//        contentGbc.gridx = 0;
//        contentGbc.weightx = 0.4; // Give less weight to the static content side
//        contentGbc.weighty = 1.0; 
//        contentPanel.add(leftPanel, contentGbc);
//
//        // Add Right Panel (with JScrollPane) to Content Panel
//        contentGbc.gridx = 1;
//        contentGbc.weightx = 0.6; // Give more weight to the scrolling side
//        contentGbc.weighty = 1.0;
//        contentPanel.add(rightPanel, contentGbc);
//
//        cardPanel.add(contentPanel, BorderLayout.CENTER);
//
//        // Center the cardPanel within the RecipeViewPanel
//        GridBagConstraints mainGbc = new GridBagConstraints();
//        mainGbc.weightx = 1.0;
//        mainGbc.weighty = 1.0;
//        mainGbc.fill = GridBagConstraints.BOTH;
//        mainGbc.insets = new Insets(30, 30, 30, 30); // Padding from edge of the screen
//        add(cardPanel, mainGbc);
//    }
//
//    // Test main
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Recipe View");
//            frame.setSize(900, 600);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setLocationRelativeTo(null);
//
//            // Create a long list of steps to force scrolling
//            List<String> longSteps = List.of(
//                    "Boil spaghetti until al dente, usually around 8-10 minutes. Drain and set aside, reserving a cup of the pasta water.",
//                    "In a large pan, brown the minced meat (beef, pork, or a mix). Drain off any excess fat from the pan.",
//                    "Add chopped onion and garlic to the meat. Sauté until the onion is soft and translucent, about 5 minutes.",
//                    "Pour in the tomato sauce, a splash of red wine (optional), and season generously with salt, pepper, oregano, and a pinch of sugar.",
//                    "Reduce the heat and let the sauce simmer for at least 30 minutes, allowing the flavors to meld and deepen.",
//                    "If the sauce becomes too thick, add a small amount of the reserved pasta water to reach the desired consistency.",
//                    "Mix the cooked spaghetti directly into the sauce. Toss well to ensure the pasta is fully coated.",
//                    "Serve immediately in bowls, topped with freshly grated Parmesan cheese and a sprig of fresh basil for garnish.",
//                    "Enjoy your classic Spaghetti Bolognese!"
//                    
//            );
//
//            RecipeViewPanel panel = new RecipeViewPanel(
//                    "Spaghetti Bolognese",
//                    "Main Course",
//                    List.of("Spaghetti (1 lb)", "Tomato Sauce (24 oz)", "Minced Meat (1 lb)", "Onion (1, chopped)", "Garlic (2 cloves)"),
//                    longSteps
//            );
//
//            frame.setContentPane(panel);
//            frame.setVisible(true);
//        });
//    }
//}
package ui.Cook;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeViewPanel extends JPanel {

    public RecipeViewPanel(String recipeName, String category, List<String> ingredients, List<String> steps) {
        // Layout and background
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 247, 250)); // Light gray background

        // --- Card Panel (Main Container) ---
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        cardPanel.setBackground(Color.WHITE);

        // --- Header ---
        JLabel titleLabel = new JLabel(recipeName);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        cardPanel.add(titleLabel, BorderLayout.NORTH);

        // --- Content Panel ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints contentGbc = new GridBagConstraints();
        contentGbc.insets = new Insets(0, 10, 0, 10);
        contentGbc.fill = GridBagConstraints.BOTH;

        // --- Left Panel (Category, Ingredients, Image) ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        JLabel categoryLabel = new JLabel("Category: " + category);
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Ingredient List
        JPanel ingredientListPanel = new JPanel();
        ingredientListPanel.setLayout(new BoxLayout(ingredientListPanel, BoxLayout.Y_AXIS));
        ingredientListPanel.setBackground(Color.WHITE);
        ingredientListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String ing : ingredients) {
            JPanel ingRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
            ingRow.setBackground(Color.WHITE);
            JLabel bullet = new JLabel("•");
            bullet.setFont(new Font("Segoe UI", Font.BOLD, 14));
            JLabel ingLabel = new JLabel(ing);
            ingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            ingRow.add(bullet);
            ingRow.add(ingLabel);
            ingredientListPanel.add(ingRow);
        }

        // Image placeholder
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(150, 150));
        imagePanel.setMaximumSize(new Dimension(150, 150));
        imagePanel.setBackground(new Color(220, 220, 220));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add everything to left panel
        leftPanel.add(categoryLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(ingredientsLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(ingredientListPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(imagePanel);
        leftPanel.add(Box.createVerticalGlue());

        // --- Right Panel (Steps) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        stepsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(stepsLabel);
        rightPanel.add(Box.createVerticalStrut(10));

        // Steps container
        JPanel stepsContentPanel = new JPanel();
        stepsContentPanel.setLayout(new BoxLayout(stepsContentPanel, BoxLayout.Y_AXIS));
        stepsContentPanel.setBackground(Color.WHITE);

        for (int i = 0; i < steps.size(); i++) {
            JPanel stepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            stepPanel.setBackground(Color.WHITE);

            JLabel stepNumber = new JLabel((i + 1) + ".");
            stepNumber.setFont(new Font("Segoe UI", Font.BOLD, 14));
            stepNumber.setForeground(new Color(43, 87, 151)); // blue tone

            JTextArea stepText = new JTextArea(steps.get(i));
            stepText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            stepText.setWrapStyleWord(true);
            stepText.setLineWrap(true);
            stepText.setEditable(false);
            stepText.setOpaque(false);
            stepText.setFocusable(false);
            stepText.setBorder(BorderFactory.createEmptyBorder());
            stepText.setPreferredSize(new Dimension(350, 40));

            stepPanel.add(stepNumber);
            stepPanel.add(stepText);

            stepsContentPanel.add(stepPanel);
            stepsContentPanel.add(Box.createVerticalStrut(8));
        }

        JScrollPane stepScroll = new JScrollPane(stepsContentPanel);
        stepScroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        stepScroll.getVerticalScrollBar().setUnitIncrement(16);
        stepScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        rightPanel.add(stepScroll);

        // Add panels to contentPanel
        contentGbc.gridx = 0;
        contentGbc.weightx = 0.4;
        contentGbc.weighty = 1.0;
        contentPanel.add(leftPanel, contentGbc);

        contentGbc.gridx = 1;
        contentGbc.weightx = 0.6;
        contentGbc.weighty = 1.0;
        contentPanel.add(rightPanel, contentGbc);

        cardPanel.add(contentPanel, BorderLayout.CENTER);

        // --- Center the card in main panel ---
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.insets = new Insets(30, 30, 30, 30);
        add(cardPanel, mainGbc);
    }

    // --- Test main method ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Recipe View");
            frame.setSize(900, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            List<String> ingredients = List.of(
                    "Spaghetti (1 lb)",
                    "Tomato Sauce (24 oz)",
                    "Minced Meat (1 lb)",
                    "Onion (1, chopped)",
                    "Garlic (2 cloves)"
            );

            List<String> steps = List.of(
                    "Boil spaghetti until al dente, usually around 8-10 minutes. Drain and set aside.",
                    "In a pan, brown the minced meat and remove excess fat.",
                    "Add chopped onion and garlic; sauté until soft and golden.",
                    "Pour in tomato sauce, add spices, and simmer for 30 minutes.",
                    "Mix cooked pasta with sauce, garnish with cheese, and serve hot."
            );

            RecipeViewPanel panel = new RecipeViewPanel(
                    "Spaghetti Bolognese",
                    "Main Course",
                    ingredients,
                    steps
            );

            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }
}
