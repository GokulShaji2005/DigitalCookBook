package ui.Cook;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.List;
import dao.recipeDao.RecipeDAO;
import dao.recipeDao.RecipeTitle;
import model.User;

public class RecipiePanel extends JPanel {
int userId;
    private JPanel recipeListPanel;
    private JButton addRecipeButton;
    public User loggedInUser;  
    public RecipiePanel(int userId) {
    	
    	  this.userId = userId;
		
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        

        // 🔹 Header
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Your Recipes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        addRecipeButton = new JButton("➕ Add Recipe");
        stylePrimaryButton(addRecipeButton);
        addRecipeButton.addActionListener(e -> {
        	JFrame addFrame = new JFrame("Add Recipe");
            addFrame.setSize(1000, 600); // adjust size
            addFrame.setLocationRelativeTo(null);
            addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close only this frame
            addFrame.setContentPane(new RecipeAddingPanel(userId)); // set your panel
            addFrame.setVisible(true);
        });

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(addRecipeButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 🔹 Recipe List
        recipeListPanel = new JPanel();
        recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));
        recipeListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

//        // Example entries (optional)
//        addRecipe("Spaghetti Bolognese");
//        addRecipe("Chocolate Cake");
//        addRecipe("Chicken Curry");
        loadRecipes();
    }

    public void loadRecipes() {
        try {
            List<RecipeTitle> recipes = RecipeTitle.getRecipeTitles(userId);
            for (RecipeTitle r : recipes) {
                addRecipe(r.getId(), r.getTitle());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
   
    public void addRecipe(int recipeId,String recipeName) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel nameLabel = new JLabel(recipeName);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        btnPanel.setBackground(new Color(250, 250, 250));

        JButton viewBtn = new JButton("View");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
    

        for (JButton btn : new JButton[]{viewBtn, editBtn, deleteBtn}) {
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            btn.setBackground(new Color(230, 230, 230));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        viewBtn.addActionListener(e -> {
        	  new RecipeViewPanel(recipeId);
        });



        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this recipe?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    RecipeDAO dao = new RecipeDAO();
                    dao.deleteRecipe(recipeId); // call your DAO method

                    // Remove the recipe card from the panel
                    recipeListPanel.remove(card);
                    recipeListPanel.revalidate();
                    recipeListPanel.repaint();

                    JOptionPane.showMessageDialog(null, "🗑️ Recipe deleted successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "❌ Error deleting recipe: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        
        btnPanel.add(viewBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        card.add(nameLabel, BorderLayout.WEST);
        card.add(btnPanel, BorderLayout.EAST);

        recipeListPanel.add(Box.createVerticalStrut(8));
        recipeListPanel.add(card);
        recipeListPanel.revalidate();
        recipeListPanel.repaint();
    }
//  public void actionPerformed(ActionEvent e) {
//  if (e.getSource() == viewBtn) {
//      openRecipeDetails(recipe.getId());
//  }
//}
    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(60, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public JButton getAddRecipeButton() {
        return addRecipeButton;
    }

    public void clearRecipes() {
        recipeListPanel.removeAll();
        recipeListPanel.revalidate();
        recipeListPanel.repaint();
    }
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Test Recipe Panel");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(500, 500);
//            frame.setLocationRelativeTo(null);
////            LoginService loginId=new LoginService();
//            RecipiePanel panel = new RecipiePanel(loginId);
//            frame.setContentPane(panel);
//
//            frame.setVisible(true);
//        });
//    }

}
