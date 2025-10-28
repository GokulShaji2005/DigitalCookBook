
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
//    private User loggedInUser;
    public UserListpanel(CardLayout cardLayout, JPanel mainContent,boolean showDelete) {
//    	 this.loggedInUser = loggedInUser;
    	setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 Header
//        boolean isViewer=false;
        JLabel header = new JLabel("Registered Chefs", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // 🔹 User list panel
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Color.WHITE);
        userListPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        try {
            UserDAO dao = new UserDAO();
            List<User> chefs = dao.getChefs();
             
            for (User user : chefs) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(250, 250, 250));
                card.setBorder(new CompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));

                JLabel name = new JLabel("👨‍🍳 " + user.getUsername());
                name.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                btnPanel.setOpaque(false);

                // 🔹 View button
                JButton viewBtn = new JButton("View");
                styleActionButton(viewBtn, new Color(46, 204, 113));

//           
                
                viewBtn.addActionListener(e -> new ChefProfile(user, true));

                btnPanel.add(viewBtn);

                // 🔹 Delete button for admin
                if (showDelete) {
                    JButton deleteBtn = new JButton("Delete");
                    styleActionButton(deleteBtn, new Color(231, 76, 60));

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

                card.add(name, BorderLayout.WEST);
                card.add(btnPanel, BorderLayout.EAST);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

                userListPanel.add(card);
                userListPanel.add(Box.createVerticalStrut(10));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading users: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(userListPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void styleActionButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(6, 10, 6, 10));

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

