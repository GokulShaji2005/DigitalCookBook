
package ui.Cook;

import model.*;
import util.StyleActionBtn;
import javax.swing.*;
import dao.recipeDao.RecipeTitle;
import dao.recipeDao.RecipeDAO;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ChefProfile extends JFrame implements ActionListener {

    private JLabel lblChefName, lblPosition, lblAvatar;
    private JPanel recipeListPanel;
    private JButton btnBack;
    private User chef;
private boolean canDeleteRecipes;

   

    private RecipeDAO fetchId = new RecipeDAO();
    
    // ✅ Constructor with viewer flag
//    public ChefProfile( User chef,boolean showDelete) {
////        this.loggedInUser = loggedInUser;
//    	   this.chef = chef; 
    public ChefProfile(User chef, boolean canDeleteRecipes) {
        this.chef = chef;
        this.canDeleteRecipes = canDeleteRecipes;
        // ===== Frame Setup =====
        setTitle("Chef Profile");
        setSize(850, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ===== Header =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(45, 110, 195));
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
        leftPanel.setPreferredSize(new Dimension(300, 0));
        leftPanel.setBackground(new Color(255, 248, 240));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // Avatar
        lblAvatar = new JLabel();
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon avatarIcon = new ImageIcon(new ImageIcon("avatar.png")
                .getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        lblAvatar.setIcon(avatarIcon);

        lblChefName = new JLabel(chef.getUsername(), SwingConstants.CENTER);
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

        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(lblRecipes, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // ===== Load Recipes =====
        loadRecipes(chef.getId());

        setVisible(true);
    }

    // ===== Load recipes by chef =====
    public void loadRecipes(int userId) {
        try {
            List<RecipeTitle> recipes = RecipeTitle.getRecipeTitles(userId);
            for (RecipeTitle r : recipes) {
                addRecipeEntry(r.getId(), r.getTitle());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // ===== Helper: Add Recipe Strip =====
    private void addRecipeEntry(int recipeId, String recipeName) {
        JPanel recipePanel = new JPanel(new BorderLayout());
        recipePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        recipePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        recipePanel.setBackground(new Color(252, 252, 252));

        JLabel lblRecipe = new JLabel("🍲 " + recipeName);
        lblRecipe.setFont(new Font("SansSerif", Font.PLAIN, 16));

        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setOpaque(false);

        // ✅ View Button
        JButton btnView = new JButton("View");
        StyleActionBtn.styleActionButton(btnView, new Color(46, 204, 113));
        btnView.addActionListener(ev -> {
            try {
                Recipe recipe = fetchId.getRecipeById(recipeId);
                if (recipe != null) {
                    new RecipeViewPanel(recipeId);
                } else {
                    JOptionPane.showMessageDialog(this, "Recipe details not found.");
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading recipe details.");
            }
        });
        btnPanel.add(btnView);
        System.out.println(chef.getRole());

//         ✅ Delete Button — Only if NOT viewer
     
//            JButton btnDelete = new JButton("Delete");
//            StyleActionBtn.styleActionButton(btnDelete, new Color(231, 76, 60));
//
//            btnDelete.addActionListener(ev -> {
//                int confirm = JOptionPane.showConfirmDialog(
//                        this,
//                        "Are you sure you want to delete \"" + recipeName + "\"?",
//                        "Confirm Delete",
//                        JOptionPane.YES_NO_OPTION
//                );
//                if (confirm == JOptionPane.YES_OPTION) {
//                    try {
//                        RecipeDAO dao = new RecipeDAO();
//                        dao.deleteRecipe(recipeId);
//                        recipeListPanel.remove(recipePanel);
//                        recipeListPanel.revalidate();
//                        recipeListPanel.repaint();
//                        JOptionPane.showMessageDialog(this, "Recipe deleted successfully.");
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(this, "Error deleting recipe.");
//                    }
//                }
//            });
//            btnPanel.add(btnDelete);
        
        if (canDeleteRecipes) {
            JButton btnDelete = new JButton("Delete");
            StyleActionBtn.styleActionButton(btnDelete, new Color(231, 76, 60));

            btnDelete.addActionListener(ev -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete \"" + recipeName + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        RecipeDAO dao = new RecipeDAO();
                        dao.deleteRecipe(recipeId);
                        recipeListPanel.remove(recipePanel);
                        recipeListPanel.revalidate();
                        recipeListPanel.repaint();
                        JOptionPane.showMessageDialog(this, "Recipe deleted successfully.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error deleting recipe.");
                    }
                }
            });

            btnPanel.add(btnDelete);
        }

//        

        recipePanel.add(lblRecipe, BorderLayout.WEST);
        recipePanel.add(btnPanel, BorderLayout.EAST);

        recipeListPanel.add(recipePanel);
        recipeListPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    }
    

    // ===== Event Handling =====
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Back".equals(e.getActionCommand())) {
            dispose(); // Just close the profile window
        }
    }
}

