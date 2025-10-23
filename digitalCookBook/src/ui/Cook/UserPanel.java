package ui.Cook;

import javax.swing.*;
import java.awt.*;
import model.User;
import util.LogoutAction;
import ui.auth.AuthUi;

public class UserPanel {
    private JFrame frame;
    private JPanel mainContent;
    private CardLayout cardLayout;
    public User loggedInUser;  
    public UserPanel(User loggedInUser) {
    	// username/password from form

    	 this.loggedInUser = loggedInUser; 
    	
   
    
        frame = new JFrame("Recipe Manager Dashboard");
        frame.setSize(1000, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // 🔹 Top Bar
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

//        JButton btnDashboard = createMenuButton("Dashboard");
        JButton btnRecipes = createMenuButton("Recipes");
//        JButton btnOthers = createMenuButton("Others");
        JButton btnLogout = createMenuButton("Logout");

//        sideMenu.add(btnDashboard);
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnRecipes);
        sideMenu.add(Box.createVerticalStrut(10));
//        sideMenu.add(btnOthers);
        sideMenu.add(Box.createVerticalGlue());
        sideMenu.add(btnLogout);

        frame.add(sideMenu, BorderLayout.WEST);

        // 🔹 Main Content Area
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        // Placeholder panels
//        JPanel dashboardPanel = createPagePanel("Dashboard Content");
        
//        User loggedInUser = loginService.loginService(username);

        RecipiePanel recipePanel = new RecipiePanel(loggedInUser.getId()); // use custom panel
        JPanel othersPanel = createPagePanel("Coming Soon...");

//        mainContent.add(dashboardPanel, "Dashboard");
        mainContent.add(recipePanel, "Recipes");
        mainContent.add(othersPanel, "Others");
//        mainContent.add(btnLogout, "Logout");
        frame.add(mainContent, BorderLayout.CENTER);

        // 🔹 Button Navigation
//        btnDashboard.addActionListener(e -> cardLayout.show(mainContent, "Dashboard"));
        btnRecipes.addActionListener(e -> cardLayout.show(mainContent, "Recipes"));
//        btnOthers.addActionListener(e -> cardLayout.show(mainContent, "Others"));
        btnLogout.addActionListener(e -> LogoutAction.performLogout(frame));


        frame.setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
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

    private JPanel createPagePanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

  
}
