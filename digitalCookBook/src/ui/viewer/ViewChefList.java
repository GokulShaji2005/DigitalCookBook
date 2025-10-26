package ui.viewer;

import javax.swing.*;
import java.awt.*;
import model.User;
import dao.userDao.UserDAO;
import ui.Cook.ChefProfile;

public class ViewChefList extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContent;

    public ViewChefList(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel header = new JLabel("Chefs", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        JPanel chefListPanel = new JPanel();
        chefListPanel.setLayout(new BoxLayout(chefListPanel, BoxLayout.Y_AXIS));
        chefListPanel.setBackground(Color.WHITE);
        chefListPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        try {
            UserDAO dao = new UserDAO();
            java.util.List<User> chefs = dao.getChefs();

            for (User chef : chefs) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(250, 250, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));

                JLabel name = new JLabel("👨‍🍳 " + chef.getUsername());
                name.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                JButton viewBtn = new JButton("View");
                viewBtn.setBackground(new Color(46, 204, 113));
                viewBtn.setForeground(Color.WHITE);
                viewBtn.setFocusPainted(false);
                viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
                viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // 👀 Viewer cannot delete recipes → pass false
//                viewBtn.addActionListener(e -> new ChefProfile(chef, false));
                viewBtn.addActionListener(e -> new ChefProfile(chef, false));

                card.add(name, BorderLayout.WEST);
                card.add(viewBtn, BorderLayout.EAST);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

                chefListPanel.add(card);
                chefListPanel.add(Box.createVerticalStrut(10));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading chefs: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(chefListPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
}
