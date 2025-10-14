package ui.Cook;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OtherPanel extends JPanel {

    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JPanel recipeListPanel;
    private List<String> recipeNames;

    public OtherPanel() {
        recipeNames = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 Top Search and Filter Section
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel searchLabel = new JLabel("Search Recipes:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        String[] filterOptions = {"All", "Dessert", "Main Course", "Appetizer"};
        filterComboBox = new JComboBox<>(filterOptions);
        filterComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(filterComboBox, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 🔹 Recipe Grid
        recipeListPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        recipeListPanel.setBackground(Color.WHITE);
        recipeListPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // 🔹 Example Recipe Cards
        addRecipeCard("Spaghetti Bolognese");
        addRecipeCard("Chocolate Cake");
        addRecipeCard("Chicken Curry");
        addRecipeCard("Veggie Pizza");
    }

    private void addRecipeCard(String recipeName) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 160));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(250, 250, 250));

        JLabel nameLabel = new JLabel(recipeName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(nameLabel, BorderLayout.CENTER);

        recipeListPanel.add(card);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }
}
