package ui.endUser;

import javax.swing.*;
import java.awt.*;
//import java.util.ArrayList;

public class RecipiePanel extends JPanel {

	private JPanel recipeListPanel;
	private JButton addRecipeButton;

	public RecipiePanel() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);


		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Color.WHITE);
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titleLabel = new JLabel("Your Recipes");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

		addRecipeButton = new JButton("Add Recipe");
		addRecipeButton.setBackground(new Color(70, 130, 180));
		addRecipeButton.setForeground(Color.WHITE);
		addRecipeButton.setFocusPainted(false);
		addRecipeButton.setFont(new Font("Arial", Font.BOLD, 14));
		addRecipeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		topPanel.add(titleLabel, BorderLayout.WEST);
		topPanel.add(addRecipeButton, BorderLayout.EAST);

		add(topPanel, BorderLayout.NORTH);


		recipeListPanel = new JPanel();
		recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));
		recipeListPanel.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane(recipeListPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); 
		add(scrollPane, BorderLayout.CENTER);

	
		addRecipe("Spaghetti Bolognese");
		addRecipe("Chocolate Cake");
		addRecipe("Chicken Curry");
	}

	
	public void addRecipe(String recipeName) {
		JPanel card = new JPanel(new BorderLayout());
		card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		card.setBackground(new Color(245, 245, 245));

	
		JLabel nameLabel = new JLabel(recipeName);
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		card.add(nameLabel, BorderLayout.WEST);


		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
		btnPanel.setBackground(new Color(245, 245, 245));

		JButton viewBtn = new JButton("View");
		JButton editBtn = new JButton("Edit");
		JButton deleteBtn = new JButton("Delete");

	
		JButton[] buttons = { viewBtn, editBtn, deleteBtn };
		for (JButton btn : buttons) {
			btn.setFocusPainted(false);
			btn.setFont(new Font("Arial", Font.PLAIN, 12));
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		btnPanel.add(viewBtn);
		btnPanel.add(editBtn);
		btnPanel.add(deleteBtn);

		card.add(btnPanel, BorderLayout.EAST);

	
		recipeListPanel.add(Box.createVerticalStrut(5)); // spacing
		recipeListPanel.add(card);

		
		recipeListPanel.revalidate();
		recipeListPanel.repaint();
	}


	public JButton getAddRecipeButton() {
		return addRecipeButton;
	}


	public void clearRecipes() {
		recipeListPanel.removeAll();
		recipeListPanel.revalidate();
		recipeListPanel.repaint();
	}
}
