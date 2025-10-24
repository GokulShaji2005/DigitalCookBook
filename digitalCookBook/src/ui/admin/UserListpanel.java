/*
 * File: UserListpanel.java
 * Author: Angelina Binoy
 * Date: 12 October 2025
 * Description:
 *     This class represents the panel displaying a list of registered chefs (users) 
 *     in the Admin Dashboard. Each chef is displayed in a card with options to 
 *     view their profile or delete them (if admin permission is granted). 
 *     The panel uses a scrollable layout to handle large numbers of users.
 */

package ui.admin;

import dao.userDao.DeleteUser;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import dao.userDao.UserDAO;
import model.User;
import ui.Cook.ChefProfile;

public class UserListpanel extends JPanel {

    /**
     * Constructor to create the User List Panel
     *
     * @param cardLayout  CardLayout from parent panel for switching views
     * @param mainContent Main content panel containing all views
     * @param showDelete  Flag to indicate if delete buttons should be shown
     */
    public UserListpanel(CardLayout cardLayout, JPanel mainContent, boolean showDelete) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 Header label
        JLabel header = new JLabel("Registered Chefs", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // 🔹 Panel to hold individual user cards
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Color.WHITE);
        userListPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        try {
            UserDAO dao = new UserDAO();
            List<User> chefs = dao.getChefs(); // Fetch all registered chefs

            for (User user : chefs) {
                // 🔹 Card panel for each user
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(250, 250, 250));
                card.setBorder(new CompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));

                // 🔹 Username label with chef emoji
                JLabel name = new JLabel("👨‍🍳 " + user.getUsername());
                name.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                // 🔹 Panel for action buttons (View, Delete)
                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                btnPanel.setOpaque(false);

                // 🔹 View profile button
                JButton viewBtn = new JButton("View");
                styleActionButton(viewBtn, new Color(46, 204, 113)); // Green button
                viewBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        new ChefProfile(user); // Open Chef Profile window
                    }
                });
                btnPanel.add(viewBtn);

                // 🔹 Delete button (only if showDelete is true)
                if (showDelete) {
                    JButton deleteBtn = new JButton("Delete");
                    styleActionButton(deleteBtn, new Color(231, 76, 60)); // Red button

                    deleteBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ev) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    UserListpanel.this,
                                    "Delete " + user.getUsername() + "?",
                                    "Confirm Delete",
                                    JOptionPane.YES_NO_OPTION
                            );

                            if (confirm == JOptionPane.YES_OPTION) {
                                try {
                                    DeleteUser delete = new DeleteUser();
                                    delete.deleteUser(user.getId(), user.getUsername());

                                    JOptionPane.showMessageDialog(
                                            UserListpanel.this,
                                            "User deleted successfully!"
                                    );

                                    // Remove card from panel and refresh UI
                                    userListPanel.remove(card);
                                    userListPanel.revalidate();
                                    userListPanel.repaint();

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(
                                            UserListpanel.this,
                                            "Error deleting user: " + ex.getMessage()
                                    );
                                }
                            }
                        }
                    });
                    btnPanel.add(deleteBtn);
                }

                // Add name and button panel to card
                card.add(name, BorderLayout.WEST);
                card.add(btnPanel, BorderLayout.EAST);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55)); // Fixed height

                // Add card to user list panel with spacing
                userListPanel.add(card);
                userListPanel.add(Box.createVerticalStrut(10));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading users: " + e.getMessage());
        }

        // Add scroll pane for the user list
        JScrollPane scrollPane = new JScrollPane(userListPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Helper method to style action buttons consistently
     *
     * @param btn     JButton to style
     * @param bgColor Background color of the button
     */
    private void styleActionButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(6, 10, 6, 10));

        // Hover effect: darken background on mouse over
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
    }
}
