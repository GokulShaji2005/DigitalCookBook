package ui.admin;

import dao.announcementDao.AnnouncementDAO;
import model.Announcements;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminAnnounce extends JPanel {
    private JPanel listPanel;
    private JButton btnCreate;
    private User adminUser;

    public AdminAnnounce(User adminUser) {
        this.adminUser = adminUser;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 🔹 Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel header = new JLabel("📢 Admin Announcements", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));

        btnCreate = new JButton("+ Create Announcement");
        stylePrimaryButton(btnCreate);
        btnCreate.addActionListener(e -> openCreateFrame());

        headerPanel.add(header, BorderLayout.WEST);
        headerPanel.add(btnCreate, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // 🔹 List of announcements
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadAnnouncements();
    }

    public void loadAnnouncements() {
        listPanel.removeAll();
        try {
            List<Announcements> announcements = new AnnouncementDAO().getAllAnnouncements();

            if (announcements.isEmpty()) {
                JLabel emptyLabel = new JLabel("No announcements yet", SwingConstants.CENTER);
                emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                emptyLabel.setForeground(Color.GRAY);
                listPanel.add(Box.createVerticalStrut(20));
                listPanel.add(emptyLabel);
            } else {
                for (Announcements a : announcements) {
                    JPanel card = createAnnouncementCard(a);
                    listPanel.add(Box.createVerticalStrut(8));
                    listPanel.add(card);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            listPanel.add(new JLabel("❌ Error loading announcements"));
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createAnnouncementCard(Announcements a) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Left: Title + Message
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(250, 250, 250));

        JLabel lblTitle = new JLabel("📌 " + a.getTitle());
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblMsg = new JLabel("<html><div style='width:400px;'>" + a.getMessage() + "</div></html>");
        lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblInfo = new JLabel("👥 " + a.getTargetAudience() + " | 🕒 " + a.getCreatedAt());
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(Color.GRAY);

        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(lblMsg);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(lblInfo);

        // Right: Delete button
        JButton btnDelete = new JButton("Delete");
        styleActionButton(btnDelete, new Color(231, 76, 60));
        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this announcement?", "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    new AnnouncementDAO().deleteAnnouncement(a.getId());
                    loadAnnouncements();
                    JOptionPane.showMessageDialog(this, "Announcement deleted successfully!");
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "❌ Error deleting announcement: " + ex.getMessage());
                }
            }
        });

        card.add(textPanel, BorderLayout.CENTER);
        card.add(btnDelete, BorderLayout.EAST);

        return card;
    }

    private void openCreateFrame() {
        new CreateAnnounce(adminUser, this);
    }

    public void refreshList() {
        loadAnnouncements();
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(60, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void styleActionButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
    }
}
