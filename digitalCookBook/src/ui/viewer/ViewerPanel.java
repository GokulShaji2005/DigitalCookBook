

package ui.viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import model.User;
import util.LogoutAction;

public class ViewerPanel {
    private JFrame frame;
    private JPanel mainContent;
    private CardLayout cardLayout;
    private User loggedInUser;

    private RecipeList recipeListPanel;
    private Favourites favouritesPanel;

    public ViewerPanel(User loggedInUser) {
        this.loggedInUser = loggedInUser;

        // Initialize favourites first, then RecipeList with reference
        favouritesPanel = new Favourites(loggedInUser);
        recipeListPanel = new RecipeList(loggedInUser, favouritesPanel);

        // JFrame setup
        frame = new JFrame("Recipe Manager - Viewer Dashboard");
        frame.setSize(1000, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Top bar and side menu
        frame.add(createTopBar(), BorderLayout.NORTH);
        frame.add(createSideMenu(), BorderLayout.WEST);

        // Main content
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.add(recipeListPanel, "Recipe");
        mainContent.add(favouritesPanel, "Favourites");
        mainContent.add(new ChefList(cardLayout, createSideMenu()), "ChefList");
        mainContent.add(new ViewerList(cardLayout, mainContent), "ViewerPanel");

        frame.add(mainContent, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(45, 110, 195));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel projectLabel = new JLabel("🍴 Recipe Manager");
        projectLabel.setForeground(Color.WHITE);
        projectLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel welcomeLabel = new JLabel("Welcome, " + loggedInUser.getUsername());
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        topBar.add(projectLabel, BorderLayout.WEST);
        topBar.add(welcomeLabel, BorderLayout.EAST);
        return topBar;
    }

    private JPanel createSideMenu() {
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(245, 247, 250));
        sideMenu.setPreferredSize(new Dimension(200, 0));
        sideMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JButton btnRecipes = createMenuButton("Recipes");
        JButton btnUsers = createMenuButton("Chefs");
        JButton btnFav = createMenuButton("Favourites");
        JButton btnLogout = createMenuButton("Logout");

        // Switch panels using anonymous inner classes
        btnRecipes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContent, "Recipe");
            }
        });

        btnFav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                favouritesPanel.loadFavouriteRecipes(); // refresh favourites
                cardLayout.show(mainContent, "Favourites");
            }
        });

        btnUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContent, "ChefList");
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogoutAction.performLogout(frame);
            }
        });

        sideMenu.add(btnRecipes);
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnUsers);
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnFav);
        sideMenu.add(Box.createVerticalGlue());
        sideMenu.add(btnLogout);

        return sideMenu;
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(60, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(80, 140, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(60, 120, 200));
            }
        });
        return btn;
    }
}


