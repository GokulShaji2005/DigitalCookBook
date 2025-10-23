
package ui.viewer;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

import dao.recipeDao.FavouriteDao;
import dao.recipeDao.RecipeTitle;
import model.User;

public class Favourites extends JPanel {

    private User loggedInUser;
    private FavouriteDao favDao;
    private JPanel recipeListPanel;

    public Favourites(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        favDao = new FavouriteDao();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 Header
        JLabel header = new JLabel("Favourites", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        recipeListPanel = new JPanel();
        recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));
        recipeListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadFavouriteRecipes();
    }

    public void loadFavouriteRecipes() {
        recipeListPanel.removeAll();
        try {
            List<Integer> favIds = favDao.getFavouriteRecipeIds(loggedInUser.getId());

            if (favIds.isEmpty()) {
                JLabel emptyLabel = new JLabel("No favourites found");
                emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                recipeListPanel.setLayout(new BorderLayout());
                recipeListPanel.add(emptyLabel, BorderLayout.CENTER);
                recipeListPanel.revalidate();
                recipeListPanel.repaint();
                return;
            }

            List<RecipeTitle> favouriteRecipes = RecipeTitle.getRecipesByIds(favIds);
            recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));

            for (RecipeTitle r : favouriteRecipes) {
                addRecipe(r.getId(), r.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        recipeListPanel.revalidate();
        recipeListPanel.repaint();
    }

    // 🔹 Add recipe card WITHOUT favourite button
    private void addRecipe(final int recipeId, String recipeName) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel nameLabel = new JLabel(recipeName);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton viewBtn = new JButton("View");
        util.StyleActionBtn.styleActionButton(viewBtn, new Color(46, 204, 113));

        // 🔹 Modern lambda ActionListener
        viewBtn.addActionListener(e -> new ui.Cook.RecipeViewPanel(recipeId));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(new Color(250, 250, 250));
        btnPanel.add(viewBtn);

        card.add(nameLabel, BorderLayout.WEST);
        card.add(btnPanel, BorderLayout.EAST);

        recipeListPanel.add(Box.createVerticalStrut(8));
        recipeListPanel.add(card);
    }
}

