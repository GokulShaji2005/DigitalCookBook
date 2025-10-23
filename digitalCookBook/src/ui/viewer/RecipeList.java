
package ui.viewer;

import java.awt.*;
import javax.swing.*;
import java.util.List;

import dao.recipeDao.FavouriteDao;
import dao.recipeDao.RecipeCategoryDao;
import dao.recipeDao.RecipeTitle;
import model.Recipe;
import model.User;
import ui.Cook.RecipeViewPanel;

public class RecipeList extends JPanel {

    protected JPanel recipeListPanel;
    protected User loggedInUser;
    private RecipeCategoryDao filterService = new RecipeCategoryDao();
    private Favourites favouritesPanel; // reference to favourites

    private JTextField searchField;
    private JComboBox<String> categoryBox;

    public RecipeList(User loggedInUser, Favourites favouritesPanel) {
        this.loggedInUser = loggedInUser;
        this.favouritesPanel = favouritesPanel;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Recipes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setBackground(Color.WHITE);

        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setToolTipText("Search recipes...");
        searchField.addActionListener(e -> searchRecipes(searchField.getText()));

        categoryBox = new JComboBox<>(new String[]{"All", "Veg", "Non-Veg", "Dessert"});
        categoryBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoryBox.addActionListener(e -> filterByCategory());

        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryBox);
        filterPanel.add(searchField);
        topPanel.add(filterPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Recipe list panel
        recipeListPanel = new JPanel();
        recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));
        recipeListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Listen to search field changes
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { searchRecipes(searchField.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { searchRecipes(searchField.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { searchRecipes(searchField.getText()); }
        });

        loadAllRecipes();
    }

    public void loadAllRecipes() {
        clearRecipes();
        try {
            List<RecipeTitle> recipes = RecipeTitle.getAllRecipes();
            for (RecipeTitle r : recipes) addRecipe(r.getId(), r.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterByCategory() {
        String category = (String) categoryBox.getSelectedItem();
        clearRecipes();
        if ("All".equals(category)) {
            loadAllRecipes();
            return;
        }
        try {
            List<Recipe> recipes = filterService.getRecipesByCategory(category);
            for (Recipe r : recipes) addRecipe(r.getRecipe_Id(), r.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchRecipes(String keyword) {
        clearRecipes();
        if (keyword.trim().isEmpty()) {
            loadAllRecipes();
            return;
        }
        try {
            List<Recipe> recipes = filterService.searchRecipes(keyword);
            for (Recipe r : recipes) addRecipe(r.getRecipe_Id(), r.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRecipe(final int recipeId, String recipeName) {
        final int userId = loggedInUser.getId();
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel nameLabel = new JLabel(recipeName);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Favourite button
        JToggleButton favBtn = new JToggleButton();
        favBtn.setFont(new Font("Arial Unicode MS", Font.PLAIN, 24));
        favBtn.setForeground(Color.RED);
        favBtn.setFocusPainted(false);
        favBtn.setContentAreaFilled(false);
        favBtn.setBorderPainted(false);
        favBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        FavouriteDao favDao = new FavouriteDao();
        boolean isFav = favDao.isFavorite(userId, recipeId);
        favBtn.setText(isFav ? "❤" : "♡");
        favBtn.setSelected(isFav);

        favBtn.addActionListener(e -> {
            try {
                if (favBtn.isSelected()) {
                    favDao.addToFavorites(userId, recipeId);
                    favBtn.setText("❤");
                } else {
                    favDao.removeFromFavorites(userId, recipeId);
                    favBtn.setText("♡");
                }
                if (favouritesPanel != null) favouritesPanel.loadFavouriteRecipes();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // View button
        JButton viewBtn = new JButton("View");
        util.StyleActionBtn.styleActionButton(viewBtn, new Color(46, 204, 113));
        viewBtn.addActionListener(e -> new RecipeViewPanel(recipeId));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(new Color(250, 250, 250));
        btnPanel.add(favBtn);
        btnPanel.add(viewBtn);

        card.add(nameLabel, BorderLayout.WEST);
        card.add(btnPanel, BorderLayout.EAST);

        recipeListPanel.add(Box.createVerticalStrut(8));
        recipeListPanel.add(card);
        recipeListPanel.revalidate();
        recipeListPanel.repaint();
    }

    protected void clearRecipes() {
        if (recipeListPanel != null) {
            recipeListPanel.removeAll();
            recipeListPanel.revalidate();
            recipeListPanel.repaint();
        }
    }
}

