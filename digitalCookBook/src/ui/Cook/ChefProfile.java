package ui.Cook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChefProfile extends JFrame implements ActionListener {

    private JLabel lblChefName, lblPosition, lblAvatar;
    private JPanel recipeListPanel;
    private JButton btnBack;

    public ChefProfile(String chefName) {

        // ===== Frame Setup =====
        setTitle("Chef Profile");
        setSize(850, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ===== Header =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        JLabel lblHeader = new JLabel("Chef Profile", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Serif", Font.BOLD, 26));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);
        add(headerPanel, BorderLayout.NORTH);

        // ===== Main Panel (Left = Chef Details, Right = Recipes) =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // ===== Left Panel - Chef Details =====
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 0)); // occupies a bit more space
        leftPanel.setBackground(new Color(255, 248, 240));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // Avatar (Profile Picture Placeholder)
        lblAvatar = new JLabel();
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 👇 Replace this path with your own avatar image file path if available
        // Example: new ImageIcon("src/images/avatar.png")
        ImageIcon avatarIcon = new ImageIcon(new ImageIcon("avatar.png")
                .getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        lblAvatar.setIcon(avatarIcon);

        lblChefName = new JLabel(chefName, SwingConstants.CENTER);
        lblChefName.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblChefName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblChefName.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        lblPosition = new JLabel("Position: Chef", SwingConstants.CENTER);
        lblPosition.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblPosition.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnBack = new JButton("Back");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(this);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(lblAvatar);
        leftPanel.add(lblChefName);
        leftPanel.add(lblPosition);
        leftPanel.add(Box.createVerticalStrut(40));
        leftPanel.add(btnBack);
        leftPanel.add(Box.createVerticalGlue());

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // ===== Right Panel - Recipe List =====
        recipeListPanel = new JPanel();
        recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));
        recipeListPanel.setBackground(Color.WHITE);
        recipeListPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblRecipes = new JLabel("Recipes by Chef", SwingConstants.CENTER);
        lblRecipes.setFont(new Font("Serif", Font.BOLD, 20));
        lblRecipes.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Scroll Pane for Recipe List
        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(lblRecipes, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // ===== Sample Recipes =====
        addRecipeEntry("Chocolate Cake");
        addRecipeEntry("Pasta Alfredo");
        addRecipeEntry("Grilled Chicken");
        addRecipeEntry("Paneer Butter Masala");
        addRecipeEntry("Strawberry Smoothie");
        addRecipeEntry("Mango Lassi");
        addRecipeEntry("French Toast");

        setVisible(true);
    }

    // ===== Helper: Add Recipe Strip =====
    private void addRecipeEntry(String recipeName) {
        JPanel recipePanel = new JPanel(new BorderLayout());
        recipePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        recipePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        recipePanel.setBackground(new Color(252, 252, 252));

        JLabel lblRecipe = new JLabel(recipeName);
        lblRecipe.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JButton btnView = new JButton("View");
        btnView.setActionCommand(recipeName);
        btnView.addActionListener(this);
        btnView.setFocusPainted(false);

        recipePanel.add(lblRecipe, BorderLayout.WEST);
        recipePanel.add(btnView, BorderLayout.EAST);

        recipeListPanel.add(recipePanel);
        recipeListPanel.add(Box.createRigidArea(new Dimension(0, 8))); // spacing
    }

    // ===== Event Handling =====
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Back")) {
            JOptionPane.showMessageDialog(this, "Returning to Dashboard...");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Viewing recipe: " + cmd);
        }
    }

    // ===== Test Run =====
    public static void main(String[] args) {
        new ChefProfile("Chef Ramesh");
    }
}
