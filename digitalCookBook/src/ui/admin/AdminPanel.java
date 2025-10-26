
package ui.admin;

import javax.swing.*;
import model.User;
import ui.Cook.ChefAnnounce;
import ui.auth.AuthUi;
import ui.viewer.ViewerAnnounce;
import util.LogoutAction;
import java.awt.*;
import java.awt.event.*;
import ui.admin.AdminAnnounce;

public class AdminPanel {
    private JFrame frame;
    private JPanel mainContent;
    private CardLayout cardLayout;
    private boolean showDelete;
    public User loggedInUser;

    public AdminPanel(User loggedInUser) {

        this.loggedInUser = loggedInUser;
        frame = new JFrame("Recipe Manager - Admin Dashboard");
        frame.setSize(1000, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // === Top Bar ===
        JPanel topBar = createTopBar();
        frame.add(topBar, BorderLayout.NORTH);

        // === Side Menu ===
        JPanel sideMenu = createSideMenu();
        frame.add(sideMenu, BorderLayout.WEST);

        // === Main Content Area ===
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        // Add panels
        boolean showDelete = true;
        mainContent.add(new UserListpanel(cardLayout, mainContent, showDelete), "UsersPanel");
        mainContent.add(new ViewerList(cardLayout, mainContent), "Viewers");
        mainContent.add(new AdminAnnounce(loggedInUser), "Announcements");
      
   

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

        JLabel welcomeLabel = new JLabel("Welcome, Admin");
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

        JButton btnUsers = createMenuButton("Chefs");
        JButton btnViewers = createMenuButton("Viewers");
        JButton btnLogout = createMenuButton("Logout");
        JButton btnAnnouncements=createMenuButton("Announcements");
        // === Traditional ActionListeners (no lambdas) ===
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
                LogoutAction.performLogout(frame);
            }
        });
        btnAnnouncements.addActionListener(e -> cardLayout.show(mainContent, "Announcements"));

        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnUsers);
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnViewers);
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(btnAnnouncements);
      
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
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(80, 140, 220)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(new Color(60, 120, 200)); }
        });
        return btn;
    }
}

