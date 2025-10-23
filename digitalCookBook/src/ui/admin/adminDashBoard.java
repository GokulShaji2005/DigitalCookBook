
package ui.admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class AdminDashBoard extends JPanel {
    public AdminDashBoard() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

        statsPanel.add(createStatCard("👥 Total Users", "25"));
        statsPanel.add(createStatCard("📚 Recipes", "80"));
        statsPanel.add(createStatCard("🕒 Active Sessions", "5"));

        add(header, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
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
}

