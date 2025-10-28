

package ui.Cook;

import dao.announcementDao.AnnouncementDAO;
import model.Announcements;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ChefAnnounce extends JPanel {
    private JPanel listPanel;
    private User loggedInUser;

    public ChefAnnounce(User loggedInUser) {
        this.loggedInUser = loggedInUser;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 🔹 Header
        JLabel header = new JLabel("📢 Announcements", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(header, BorderLayout.NORTH);

        // 🔹 List Panel
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
            List<Announcements> announcements = new AnnouncementDAO().getAnnouncementsForRole("Chef");

            if (announcements.isEmpty()) {
                JLabel emptyLabel = new JLabel("No announcements yet.", SwingConstants.CENTER);
                emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                emptyLabel.setForeground(Color.GRAY);

                JPanel emptyPanel = new JPanel(new BorderLayout());
                emptyPanel.setBackground(Color.WHITE);
                emptyPanel.add(emptyLabel, BorderLayout.CENTER);
                listPanel.add(emptyPanel);
            } else {
                for (Announcements a : announcements) {
                    listPanel.add(createAnnouncementCard(a));
                    listPanel.add(Box.createVerticalStrut(10));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("❌ Error loading announcements", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            listPanel.add(errorLabel);
        }

        revalidate();
        repaint();
    }

    private JPanel createAnnouncementCard(Announcements a) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setBackground(new Color(247, 249, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Left: Title + Message
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(a.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(40, 40, 40));

        JLabel messageLabel = new JLabel(a.getMessage());
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(70, 70, 70));

        JLabel infoLabel = new JLabel("Audience: " + a.getTargetAudience() + " | " + a.getCreatedAt());
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        infoLabel.setForeground(Color.GRAY);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(messageLabel);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(infoLabel);

        card.add(textPanel, BorderLayout.WEST);

        return card;
    }
}

