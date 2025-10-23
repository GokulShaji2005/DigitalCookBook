
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

    public ViewerList(CardLayout cardLayout, JPanel mainContent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel header = new JLabel("Registered Viewers", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(new EmptyBorder(20, 0, 10, 0));

        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Color.WHITE);
        userListPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        try {
            UserDAO dao = new UserDAO();
            List<User> viewers = dao.getViewers(); // 🔹 Get viewers from DB

            for (User user : viewers) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(250, 250, 250));
                card.setBorder(new CompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));

                JLabel name = new JLabel("👤 " + user.getUsername());
                name.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                btnPanel.setOpaque(false);

                JButton deleteBtn = new JButton("Delete");
                styleActionButton(deleteBtn, new Color(231, 76, 60));

                // === Traditional ActionListener ===
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
                                delete.deleteUser(user.getId(), user.getUsername()); // Delete user in DB
                                JOptionPane.showMessageDialog(ViewerList.this, "User deleted successfully!");

                                // Remove user card from UI
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

        add(header, BorderLayout.NORTH);
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

