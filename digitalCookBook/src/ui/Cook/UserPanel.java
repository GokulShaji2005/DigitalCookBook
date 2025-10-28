

package ui.Cook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;
import util.LogoutAction;

public class UserPanel {
    private JFrame frame;
    private JPanel mainContent;
    private CardLayout cardLayout;
    public User loggedInUser;  

    public UserPanel(User loggedInUser) {
        this.loggedInUser = loggedInUser; 

        frame = new JFrame("Recipe Manager Dashboard");
        frame.setSize(1000, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());


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
      frame.add(topBar, BorderLayout.NORTH);


        // 🔹 Side Menu
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(245, 247, 250));
        sideMenu.setPreferredSize(new Dimension(200, 0));
        sideMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JButton btnRecipes = createMenuButton("Recipes");
        JButton btnAnnouncements = createMenuButton("Announcements");
        JButton btnLogout = createMenuButton("Logout");

        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnRecipes);
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnAnnouncements);
        sideMenu.add(Box.createVerticalGlue());
        sideMenu.add(btnLogout);

        frame.add(sideMenu, BorderLayout.WEST);

        // 🔹 Main Content Area
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        // Custom Panels
        RecipiePanel recipePanel = new RecipiePanel(loggedInUser.getId());
        ChefAnnounce chefAnnouncePanel = new ChefAnnounce(loggedInUser);

        mainContent.add(recipePanel, "Recipes");
        mainContent.add(chefAnnouncePanel, "Announcements");

        frame.add(mainContent, BorderLayout.CENTER);

        // 🔹 Button Navigation with ActionListeners
        btnRecipes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContent, "Recipes");
            }
        });

        btnAnnouncements.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContent, "Announcements");
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogoutAction.performLogout(frame);
            }
        });

        frame.setVisible(true);
    }

    private JButton createMenuButton(final String text) {
        final JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(180, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBackground(new Color(60, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 140, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 120, 200));
            }
        });

        return button;
    }
}

