package ui.admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashBoard extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public AdminDashBoard() {
        setTitle("Recipe Manager - Admin Dashboard");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Sidebar ===
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(45, 110, 195)); // Blue tone consistent with user panels
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel projectName = new JLabel("🍴 Admin Panel", SwingConstants.CENTER);
        projectName.setForeground(Color.WHITE);
        projectName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        projectName.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(projectName);
        sidebar.add(Box.createVerticalStrut(30));

        JButton btnDashboard = createSidebarButton("Dashboard");
        JButton btnUsers = createSidebarButton("Users");
        JButton btnSignout = createSidebarButton("Logout");

        sidebar.add(btnDashboard);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnUsers);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnSignout);

        // === Main Content ===
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel dashboardPanel = createDashboardPanel();
        JPanel usersPanel = createUsersPanel();
        JPanel userRecipesPanel = createUserRecipesPanel();

        mainPanel.add(dashboardPanel, "Dashboard");
        mainPanel.add(usersPanel, "UsersPanel");
        mainPanel.add(userRecipesPanel, "UserRecipesPanel");

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // === Button navigation ===
        btnDashboard.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));
        btnUsers.addActionListener(e -> cardLayout.show(mainPanel, "UsersPanel"));
        btnSignout.addActionListener(e -> dispose());

        setVisible(true);
    }

    // === Sidebar button style ===
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(60, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(new EmptyBorder(8, 12, 8, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(80, 140, 220));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(60, 120, 200));
            }
        });
        return btn;
    }

    // === Dashboard overview panel ===
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

        statsPanel.add(createStatCard("👥 Total Users", "25"));
        statsPanel.add(createStatCard("📚 Recipes", "80"));
        statsPanel.add(createStatCard("🕒 Active Sessions", "5"));

        panel.add(header, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(new Color(45, 110, 195));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    // === Users List Panel ===
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel header = new JLabel("Registered Users", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(new EmptyBorder(20, 0, 10, 0));

        String[] users = {"John", "Jane", "Alex", "Priya"};

        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Color.WHITE);
        userListPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        for (String user : users) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(new Color(250, 250, 250));
            card.setBorder(new CompoundBorder(
                    new LineBorder(new Color(220, 220, 220), 1, true),
                    new EmptyBorder(10, 15, 10, 15)
            ));

            JLabel name = new JLabel("👤 " + user);
            name.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            btnPanel.setOpaque(false);

            JButton viewBtn = new JButton("View");
            JButton deleteBtn = new JButton("Delete");

            styleActionButton(viewBtn, new Color(46, 204, 113));
            styleActionButton(deleteBtn, new Color(231, 76, 60));

            btnPanel.add(viewBtn);
            btnPanel.add(deleteBtn);

            card.add(name, BorderLayout.WEST);
            card.add(btnPanel, BorderLayout.EAST);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
            userListPanel.add(card);
            userListPanel.add(Box.createVerticalStrut(10));

            viewBtn.addActionListener(e -> cardLayout.show(mainPanel, "UserRecipesPanel"));
        }

        JScrollPane scrollPane = new JScrollPane(userListPanel);
        scrollPane.setBorder(null);

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // === User Recipes Panel ===
    private JPanel createUserRecipesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton backBtn = new JButton("← Back");
        styleActionButton(backBtn, new Color(52, 73, 94));

        JLabel userLabel = new JLabel("User: John Doe", SwingConstants.CENTER);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        topPanel.add(backBtn, BorderLayout.WEST);
        topPanel.add(userLabel, BorderLayout.CENTER);

        String[][] recipes = {{"Biryani"}, {"Butter Chicken"}, {"Pasta Alfredo"}};

        JPanel recipeListPanel = new JPanel();
        recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));
        recipeListPanel.setBackground(Color.WHITE);
        recipeListPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        for (String[] recipe : recipes) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(new Color(250, 250, 250));
            card.setBorder(new CompoundBorder(
                    new LineBorder(new Color(220, 220, 220), 1, true),
                    new EmptyBorder(10, 15, 10, 15)
            ));

            JLabel name = new JLabel("🍲 " + recipe[0]);
            name.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            btnPanel.setOpaque(false);

            JButton viewBtn = new JButton("View");
            JButton editBtn = new JButton("Edit");
            JButton deleteBtn = new JButton("Delete");

            styleActionButton(viewBtn, new Color(46, 204, 113));
            styleActionButton(editBtn, new Color(241, 196, 15));
            styleActionButton(deleteBtn, new Color(231, 76, 60));

            btnPanel.add(viewBtn);
            btnPanel.add(editBtn);
            btnPanel.add(deleteBtn);

            card.add(name, BorderLayout.WEST);
            card.add(btnPanel, BorderLayout.EAST);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
            recipeListPanel.add(card);
            recipeListPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setBorder(null);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "UsersPanel"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // === Action button style (consistent with user panels) ===
 // === Action button style (consistent with user panels) ===
    private void styleActionButton(JButton btn, Color bgColor) {
        // ... method body ...
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
    } // Closes styleActionButton method

    public static void main(String[] args) {
    	new AdminDashBoard();
    }
} // Closes AdminDashBoard class