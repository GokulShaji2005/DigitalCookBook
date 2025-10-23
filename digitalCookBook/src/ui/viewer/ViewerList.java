

package ui.viewer;

import dao.userDao.UserDAO;
import model.User;
import ui.Cook.ChefProfile;
import util.StyleActionBtn;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class ViewerList extends JPanel {

    public ViewerList(CardLayout cardLayout, JPanel mainContent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 Header
        JLabel header = new JLabel("Registered Chefs", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(new EmptyBorder(20, 0, 10, 0));

        // 🔹 User list panel
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Color.WHITE);
        userListPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        try {
            UserDAO dao = new UserDAO();
            List<User> chefs = dao.getChefs(); // Get chefs from DB

            for (User user : chefs) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(250, 250, 250));
                card.setBorder(new CompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

                // 🔹 User name
                JLabel name = new JLabel(user.getUsername());
                name.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                // 🔹 Buttons panel
                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                btnPanel.setOpaque(false);

                JButton viewBtn = new JButton("View");
                StyleActionBtn.styleActionButton(viewBtn, new Color(46, 204, 113));
                viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                viewBtn.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this,
                            "👤 Viewing Chef: " + user.getUsername());
                    new ChefProfile(user);
                });

                btnPanel.add(viewBtn);

                card.add(name, BorderLayout.WEST);
                card.add(btnPanel, BorderLayout.EAST);

                userListPanel.add(card);
                userListPanel.add(Box.createVerticalStrut(10));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading users: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(userListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}

