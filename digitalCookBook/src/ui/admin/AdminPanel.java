/*
 * File: AdminPanel.java
 * Author: Angelina Binoy
 * Date: 11 October 2025
 * Description:
 *     This class creates the main Admin Panel GUI for the Recipe Manager application.
 *     It includes a top bar with project info, a side menu for navigation, and a 
 *     main content area using CardLayout to switch between different admin views 
 *     such as Chefs (UsersPanel) and Viewers. Logout functionality is also included.
 */

package ui.admin;

import javax.swing.*;
import model.User;
import ui.auth.AuthUi;
import util.LogoutAction;
import java.awt.*;
import java.awt.event.*;

public class AdminPanel {

    private JFrame frame;            // Main frame for the admin panel
    private JPanel mainContent;      // Panel to hold different views using CardLayout
    private CardLayout cardLayout;   // Layout manager to switch between panels
    private boolean showDelete;      // Flag for showing delete options in UserListPanel
    public User loggedInUser;        // Currently logged-in admin user

    // Constructor initializes the Admin Panel GUI
    public AdminPanel(User loggedInUser) {
        this.loggedInUser = loggedInUser;

        // === Main JFrame setup ===
        frame = new JFrame("Recipe Manager - Admin Dashboard");
        frame.setSize(1000, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);          // Center on screen
        frame.setLayout(new BorderLayout());

        // === Top Bar (header) ===
        JPanel topBar = createTopBar();
        frame.add(topBar, BorderLayout.NORTH);

        // === Side Menu (navigation) ===
        JPanel sideMenu = createSideMenu();
        frame.add(sideMenu, BorderLayout.WEST);

        // === Main Content Area ===
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        // Add panels to the CardLayout
        boolean showDelete = true;  // Option to show delete buttons in user panel
        mainContent.add(new UserListpanel(cardLayout, mainContent, showDelete), "UsersPanel");
        mainContent.add(new ViewerList(cardLayout, mainContent), "Viewers");

        frame.add(mainContent, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }

    /**
     * Creates the top bar with project title and welcome message.
     */
    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(45, 110, 195));  // Blue background
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel projectLabel = new JLabel("🍴 Recipe Manager");
        projectLabel.setForeground(Color.WHITE);
        projectLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        topBar.add(projectLabel, BorderLayout.WEST);
        topBar.add(welcomeLabel, BorderLayout.EAST);

        return topBar;
    }

    /**
     * Creates the side menu with navigation buttons: Chefs, Viewers, Logout.
     */
    private JPanel createSideMenu() {
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(245, 247, 250));
        sideMenu.setPreferredSize(new Dimension(200, 0));
        sideMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        // Menu buttons
        JButton btnUsers = createMenuButton("Chefs");
        JButton btnViewers = createMenuButton("Viewers");
        JButton btnLogout = createMenuButton("Logout");

        // Button actions (switch panels or logout)
        btnUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContent, "UsersPanel");
            }
        });

        btnViewers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContent, "Viewers");
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogoutAction.performLogout(frame); // Perform logout
            }
        });

        // Add buttons to the side menu with spacing
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnUsers);
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnViewers);
        sideMenu.add(Box.createVerticalGlue());  // Push logout to bottom
        sideMenu.add(btnLogout);

        return sideMenu;
    }

    /**
     * Helper method to create a styled side menu button with hover effects.
     */
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

        // Hover effect to lighten button color on mouse over
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(80, 140, 220)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(new Color(60, 120, 200)); }
        });

        return btn;
    }
}