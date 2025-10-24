/*
 * File: AdminDashBoard.java
 * Author: Angelina Binoy
 * Date: 11 October 2025
 * Description: 
 *     This class defines the Admin Dashboard panel for the application. 
 *     It displays key statistics like total users, number of recipes, and active sessions
 *     using a visually appealing card layout with Swing components.
 */

package ui.admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class AdminDashBoard extends JPanel {

    // Constructor to initialize the Admin Dashboard panel
    public AdminDashBoard() {
        // Set the main layout and background color
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Create and configure the header label
        JLabel header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));  // Set font style and size
        header.setBorder(new EmptyBorder(20, 0, 20, 0));       // Add top and bottom padding

        // Create a panel to hold statistic cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0)); 
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(new EmptyBorder(40, 60, 40, 60));       // Add padding around cards

        // Add individual statistic cards to the stats panel
        statsPanel.add(createStatCard("👥 Total Users", "25"));
        statsPanel.add(createStatCard("📚 Recipes", "80"));
        statsPanel.add(createStatCard("🕒 Active Sessions", "5"));

        // Add header and stats panel to the main panel
        add(header, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(250, 250, 250)); // Light gray background
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1, true), // Rounded border with light gray line
                new EmptyBorder(20, 20, 20, 20)                    // Padding inside the card
        ));

        // Title label for the card
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Value label (centered, bold, colored)
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(new Color(45, 110, 195)); // Blue color for emphasis

        // Add labels to card layout
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
