/*
 * File: ViewerList.java
 * Author: Angelina Binoy
 * Date: 12 October 2025
 * Description:
 *     This class represents the panel displaying a list of registered viewers 
 *     in the Admin Dashboard. Each viewer is displayed in a card with an option 
 *     to delete them. The panel is scrollable to accommodate many viewers, 
 *     and uses consistent styling for buttons and cards.
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

public class ViewerList extends JPanel {

    /**
     * Constructor to create the Viewer List Panel
     *
     * @param cardLayout  CardLayout from parent panel for switching views
     * @param mainContent Main content panel containing all views
     */
    public ViewerList(CardLayout cardLayout, JPanel mainContent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 Header label
        JLabel header = new JLabel("Registered Viewers", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(new EmptyBorder(20, 0, 10, 0));

        // 🔹 Panel to hold viewer cards
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Color.WHITE);
        userListPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        try {
            UserDAO dao = new UserDAO();
            List<User> viewers = dao.getViewers(); // Fetch all registered viewers

            for (User user : viewers) {
                // 🔹 Card panel for each viewer
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(250, 250, 250));
                card.setBorder(new CompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));

                // 🔹 Viewer name label with icon
                JLabel name = new JLabel("👤 " + user.getUsername());
                name.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                // 🔹 Panel for action buttons (Delete)
                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                btnPanel.setOpaque(false);

                JButton deleteBtn = new JButton("Delete");
                styleActionButton(deleteBtn, new Color(231, 76, 60)); // Red delete button

                // 🔹 Delete button action
                deleteBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        int confirm = JOptionPane.showConfirmDialog(
                                ViewerList.this,
                                "Delete " + user.getUsername() + "?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                DeleteUser delete = new DeleteUser();
                                delete.deleteUser(user.getId(), user.getUsername()); // Delete from DB
                                JOptionPane.showMessageDialog(ViewerList.this, "User deleted successfully!");

                                // Remove card from UI and refresh
                                userListPanel.remove(card);
                                userListPanel.revalidate();
                                userListPanel.repaint();

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(
                                        ViewerList.this,
                                        "Error deleting user: " + ex.getMessage()
                                );
                            }
                        }
                    }
                });

                btnPanel.add(deleteBtn);

                // Add name and button panel to card
                card.add(name, BorderLayout.WEST);
                card.add(btnPanel, BorderLayout.EAST);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55)); // Fixed card height

                // Add card to user list panel with spacing
                userListPanel.add(card);
                userListPanel.add(Box.createVerticalStrut(10));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading users: " + e.getMessage());
        }

        // 🔹 Add scroll pane for the user list
        JScrollPane scrollPane = new JScrollPane(userListPanel);
        scrollPane.setBorder(null);

        // Add components to main panel
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Helper method to style action buttons consistently
     *
     * @param btn     JButton to style
     * @param bgColor Background color for the button
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
